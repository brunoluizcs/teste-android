package com.easynvest.investments.features.resultform

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.easynvest.domain.model.InvestmentResponse
import com.easynvest.investments.R
import com.easynvest.investments.databinding.ActivityResultBinding
import com.easynvest.views.extensions.toCurrency
import java.text.SimpleDateFormat
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    companion object {
        fun getLaunchIntent(context: Context, response: InvestmentResponse) =
            Intent(context, ResultActivity::class.java).apply {
                putExtra("extra-data", response)
            }
    }

    private var response: InvestmentResponse? = null
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)

        binding.btnSimulateAgain.setOnClickListener { finish() }

        response = intent.getParcelableExtra("extra-data")
        response?.apply { drawResultValues(this) }
    }

    private fun drawResultValues(response: InvestmentResponse) {
        binding.tvSimulationResult.text = response.grossAmount.toCurrency()
        binding.tvLabelTotalProfits.text = getString(R.string.label_total_profits,
            response.grossAmountProfit.toCurrency())
        binding.tvInvestedAmount.text2 = response.investmentParameter.investedAmount.toCurrency()
        binding.tvGrossAmount.text2 = response.grossAmount.toCurrency()
        binding.tvGrossAmountProfit.text2 = response.grossAmountProfit.toCurrency()
        binding.tvTaxesAmount.text2 = "${response.taxesAmount.toCurrency()}[${response.taxesRate.toCurrency()}%]"
        binding.tvNetAmount.text2 = response.netAmount.toCurrency()
        binding.tvMaturityDate.text2 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            .format(response.investmentParameter.maturityDate)
        binding.tvMaturityBusinessDays.text2 = response.investmentParameter.maturityTotalDays.toString()
        binding.tvMonthlyGrossRateProfit.text2 = "${response.monthlyGrossRateProfit}%"
        binding.tvRate.text2 = "${response.investmentParameter.rate}%"
        binding.tvAnnualGrossRateProfit.text2 = "${response.annualGrossRateProfit}%"
        binding.tvRateProfit.text2 = "${response.rateProfit}%"
    }
}