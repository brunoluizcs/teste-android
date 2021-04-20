package com.easynvest.investments.features.form

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.easynvest.domain.model.InvestmentResponse
import com.easynvest.investments.R
import com.easynvest.investments.ViewState
import com.easynvest.investments.databinding.ActivityFormBinding
import com.easynvest.views.watchers.CurrencyTextWatcher
import com.easynvest.views.watchers.DateTextWatcher
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
        viewModel.onInvestmentData.observe(this, Observer<ViewState<InvestmentResponse>> { state ->
            when (state) {
                ViewState.Loading -> {}
                is ViewState.Failure -> {}
                is ViewState.Success -> {
                    Toast.makeText(this@FormActivity,
                        "${state.result.annualGrossRateProfit}",
                        Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupListeners() {
        viewModel.formValidatorData.observe(this, Observer {
            binding.btnSimulate.isEnabled = it
        })

        binding.edtAmount.addTextChangedListener(CurrencyTextWatcher().apply {
            onCurrencyChanged = {viewModel.amount = it}
        })

        binding.edtRate.addTextChangedListener(CurrencyTextWatcher().apply {
            onCurrencyChanged = {viewModel.rate = it}
        })

        binding.edtMaturityDate.addTextChangedListener(DateTextWatcher().apply {
            onDateChanged = { viewModel.maturityDate = it}
        })
    }

    private fun onBtnSimulateClicked(view: View) {
        viewModel.simulateInvestment()
    }
}
