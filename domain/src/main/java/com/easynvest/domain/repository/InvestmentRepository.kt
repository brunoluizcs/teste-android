package com.easynvest.domain.repository

import com.easynvest.domain.model.InvestmentRequest
import com.easynvest.domain.model.InvestmentResponse

interface InvestmentRepository {
    suspend fun fetchInvestmentData(request: InvestmentRequest): InvestmentResponse
}
