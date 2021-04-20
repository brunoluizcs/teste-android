package com.easynvest.domain.usecase

import com.easynvest.domain.model.InvestmentRequest
import com.easynvest.domain.repository.InvestmentRepository

class FetchInvestmentUseCase(private val repository: InvestmentRepository) {

    suspend fun execute(request: InvestmentRequest) =
        repository.fetchInvestmentData(request)
}
