package com.easynvest.data.repository

import com.easynvest.data.remote.api.Api
import com.easynvest.domain.model.InvestmentRequest
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class InvestmentRepositoryImpTest {

    companion object {
        const val STR_RESPONSE = "{\n" +
            "    \"investmentParameter\": {\n" +
            "        \"investedAmount\": 32323.0,                      // O valor a ser investido\n" +
            "        \"yearlyInterestRate\": 9.5512,                   // Rentabilidade anual\n" +
            "        \"maturityTotalDays\": 1981,                      // Dias corridos\n" +
            "        \"maturityBusinessDays\": 1409,                   // Dias úteis\n" +
            "        \"maturityDate\": \"2023-03-03T00:00:00\",          // Data de vencimento\n" +
            "        \"rate\": 123.0,                                  // Percentual do papel\n" +
            "        \"isTaxFree\": false                              // Isento de IR\n" +
            "    },\n" +
            "    \"grossAmount\": 60528.20,                            // Valor bruto do investimento\n" +
            "    \"taxesAmount\": 4230.78,                             // Valor do IR\n" +
            "    \"netAmount\": 56297.42,                              // Valor líquido\n" +
            "    \"grossAmountProfit\": 28205.20,                      // Rentabilidade bruta\n" +
            "    \"netAmountProfit\": 23974.42,                        // Rentabilidade líquida\n" +
            "    \"annualGrossRateProfit\": 87.26,                     // Rentabilidade bruta anual\n" +
            "    \"monthlyGrossRateProfit\": 0.76,                     // Rentabilidade bruta mensal\n" +
            "    \"dailyGrossRateProfit\": 0.000445330025305748,       // Rentabilidade bruta diária\n" +
            "    \"taxesRate\": 15.0,                                  // Faixa do IR (%)\n" +
            "    \"rateProfit\": 9.5512,                               // Rentabilidade no período\n" +
            "    \"annualNetRateProfit\": 74.17                        // Rentabilidade líquida anual\n" +
            "}"
    }

    private val api = mockk<Api>(relaxed = true)
    private val request = mockk<InvestmentRequest>(relaxed = true)

    @Test
    fun when_fetchInvestmentData_then_fetchInvestmentPrediction() = runBlocking {
        val repository = InvestmentRepositoryImp(api)
        coEvery { api.fetchInvestmentPrediction(any(), any(), any(), any(), any()) } returns STR_RESPONSE
        repository.fetchInvestmentData(request)

        coVerify(exactly = 1) { api.fetchInvestmentPrediction(any(), any(), any(), any(), any()) }
    }

    @Test
    fun when_fetchInvestmentData_then_return_InvestmentResponse() = runBlocking {
        val repository = InvestmentRepositoryImp(api)
        coEvery { api.fetchInvestmentPrediction(any(), any(), any(), any(), any()) } returns STR_RESPONSE

        val response = repository.fetchInvestmentData(request)

        Truth.assertThat(response.investmentParameter.investedAmount).isEqualTo(32323.0)
        Truth.assertThat(response.annualNetRateProfit).isEqualTo(74.17)
    }
}
