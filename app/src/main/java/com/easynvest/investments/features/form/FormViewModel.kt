package com.easynvest.investments.features.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easynvest.domain.model.InvestmentRequest
import com.easynvest.domain.model.InvestmentResponse
import com.easynvest.domain.usecase.FetchInvestmentUseCase
import com.easynvest.investments.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Date

class FormViewModel(
    private val useCase: FetchInvestmentUseCase,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(dispatcher + viewModelJob)

    private val _liveDataAmount = MutableLiveData<Double?>()
    private val _liveDataRate = MutableLiveData<Double?>()
    private val _liveDataMaturityDate = MutableLiveData<Date?>()

    private val _onInvestmentData = MutableLiveData<ViewState<InvestmentResponse>>()
    val onInvestmentData: LiveData<ViewState<InvestmentResponse>> = _onInvestmentData

    val formValidatorData = MediatorLiveData<Boolean>().apply {
        addSource(_liveDataAmount) {
            this.postValue(
                isFormValid(amountValue = it, rateValue = rate, maturityDateValue = maturityDate)
            )
        }
        addSource(_liveDataRate) {
            this.postValue(
                isFormValid(amountValue = amount, rateValue = it, maturityDateValue = maturityDate)
            )
        }
        addSource(_liveDataMaturityDate) {
            this.postValue(
                isFormValid(amountValue = amount, rateValue = rate, maturityDateValue = it)
            )
        }
    }

    var amount: Double? = null
        set(value) {
            _liveDataAmount.postValue(value)
            field = value
        }

    var rate: Double? = null
        set(value) {
            _liveDataRate.postValue(value)
            field = value
        }

    var maturityDate: Date? = null
        set(value) {
            _liveDataMaturityDate.postValue(value)
            field = value
        }

    fun simulateInvestment() {
        ioScope.launch {
            try {
                _onInvestmentData.postValue(ViewState.Loading)
                val request = InvestmentRequest(
                    amount ?: 0.0,
                    "CDI",
                    rate ?: 0.0, false, maturityDate ?: Date()
                )
                val response = useCase.execute(request)
                _onInvestmentData.postValue(ViewState.Success(response))
            } catch (e: Exception) {
                _onInvestmentData.postValue(ViewState.Failure(e))
            }
        }
    }

    private fun isValidMaturityDate(maturityDate: Date?) = maturityDate != null
    private fun isValidAmount(amount: Double?) = amount ?: 0.0 > 0.0
    private fun isValidRate(rate: Double?) = rate ?: 0.0 > 0.0
    private fun isFormValid(amountValue: Double?, rateValue: Double?, maturityDateValue: Date?) =
        isValidAmount(amountValue) &&
            isValidRate(rateValue) &&
            isValidMaturityDate(maturityDateValue)

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}
