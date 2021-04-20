package com.easynvest.domain.usecase

import com.easynvest.domain.model.InvestmentRequest
import com.easynvest.domain.model.InvestmentResponse
import com.easynvest.domain.repository.InvestmentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class FetchInvestmentUseCaseTest {

    @Test
    fun when_execute_should_call_fetchInvestmentData() = runBlockingTest {
        val repository = mockk<InvestmentRepository>()
        val response = mockk<InvestmentResponse>()
        coEvery { repository.fetchInvestmentData(any()) } returns response

        val request = mockk<InvestmentRequest>()
        val useCase = FetchInvestmentUseCase(repository)
        useCase.execute(request)

        coVerify(exactly = 1) { repository.fetchInvestmentData(any()) }
    }
}
