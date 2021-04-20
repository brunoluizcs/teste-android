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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
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
    val onInvestmentData : LiveData<ViewState<InvestmentResponse>> = _onInvestmentData

    val formValidatorData = MediatorLiveData<Boolean>().apply {
        addSource(_liveDataAmount) {
            this.postValue(isValidAmount(it))
        }
        addSource(_liveDataRate) {
            this.postValue(isValidRate(it))
        }
        addSource(_liveDataMaturityDate) {
            this.postValue(isValidMaturityDate(it))
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
            try{
                val request = InvestmentRequest(amount ?: 0.0,
                    "CDI",
                    rate ?: 0.0,false, maturityDate ?: Date())
                val response = useCase.execute(request)
                _onInvestmentData.postValue(ViewState.Success(response))
            }catch (e: Exception) {
                _onInvestmentData.postValue(ViewState.Failure(e))
            }
        }
    }

    private fun isValidMaturityDate(maturityDate: Date?) = maturityDate != null
    private fun isValidAmount(amount: Double?) = amount ?: 0.0 > 0.0
    private fun isValidRate(rate: Double?) = rate ?: 0.0 > 0.0


    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}