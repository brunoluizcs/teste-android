package com.easynvest.investments.features.form

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.easynvest.domain.model.InvestmentResponse
import com.easynvest.investments.R
import com.easynvest.investments.ViewState
import com.easynvest.investments.databinding.ActivityFormBinding
import com.easynvest.investments.features.resultform.ResultActivity
import com.easynvest.views.extensions.visible
import com.easynvest.views.watchers.CurrencyTextWatcher
import com.easynvest.views.watchers.DateTextWatcher
import com.easynvest.views.watchers.PercentageTextWatcher
import org.koin.androidx.viewmodel.ext.android.viewModel


class FormActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFormBinding

    val viewModel: FormViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form)

        binding.btnSimulate.setOnClickListener(this::onBtnSimulateClicked)
        setupListeners()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.formValidatorData.observe(this) {
            binding.btnSimulate.isEnabled = it
        }

        viewModel.onInvestmentData.observe(this) { state ->
            when (state) {
                ViewState.Loading -> {
                    setVisibility(isLoading = true)
                }
                is ViewState.Failure -> {
                    setVisibility(isFailure = state.error)
                }
                is ViewState.Success -> {
                    setVisibility(response = state.result)
                }
            }
        }
    }

    private fun setVisibility(
        isLoading: Boolean = false,
        isFailure: Exception? = null,
        response: InvestmentResponse? = null) {

        response?.let {
            startActivity(ResultActivity.getLaunchIntent(this, it))
        }

        isFailure?.let {
            Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
        }

        binding.containerForm.visible(! isLoading)
        binding.containerLoading.visible(isLoading)
    }

    private fun setupListeners() {
        binding.edtAmount.addTextChangedListener(CurrencyTextWatcher(binding.edtAmount).apply {
            onCurrencyChanged = { viewModel.amount = it }
        })

        binding.edtRate.addTextChangedListener(PercentageTextWatcher().apply {
            onPercentageChange = { viewModel.rate = it }
        })

        binding.edtMaturityDate.addTextChangedListener(DateTextWatcher(binding.edtMaturityDate).apply {
            onDateChanged = { viewModel.maturityDate = it }
        })
    }

    private fun onBtnSimulateClicked(view: View) {
        viewModel.simulateInvestment()
    }
}
