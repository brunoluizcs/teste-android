package com.easynvest.data.repository

import com.easynvest.data.remote.api.Api
import com.easynvest.domain.model.InvestmentRequest
import com.easynvest.domain.model.InvestmentResponse
import com.easynvest.domain.repository.InvestmentRepository
import com.google.gson.GsonBuilder
import java.text.SimpleDateFormat

class InvestmentRepositoryImp(private val api: Api) : InvestmentRepository {

    override suspend fun fetchInvestmentData(request: InvestmentRequest): InvestmentResponse {
        val result = api.fetchInvestmentPrediction(
            investedAmount =  request.investedAmount,
            index = request.index,
            rate = request.rate,
            maturityDate = SimpleDateFormat("yyyy-MM-dd").format(request.maturityDate),
            isTaxFree = false
        )
        return GsonBuilder().create().fromJson(
            result.replace(Regex("//.+"), ""),
            InvestmentResponse::class.java
        )
    }
}