package com.easynvest.investments.features.form

import com.easynvest.domain.model.InvestmentResponse
import com.easynvest.domain.repository.InvestmentRepository
import com.easynvest.domain.usecase.FetchInvestmentUseCase
import com.easynvest.investments.ViewState
import com.easynvest.investments.features.BaseInstantExecutorUnitTest
import com.easynvest.investments.utils.getValueForTest
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Date

@RunWith(JUnit4::class)
class FormViewModelTest : BaseInstantExecutorUnitTest() {

    val repository: InvestmentRepository = mockk(relaxed = true)

    @Test
    fun when_fetchInvestmentData_should_success() {
        val response: InvestmentResponse = mockk(relaxed = true)
        coEvery { repository.fetchInvestmentData(any()) } returns response

        val formViewModel = FormViewModel(
            useCase = FetchInvestmentUseCase(repository),
            dispatcher = Dispatchers.Main
        )

        formViewModel.simulateInvestment()
        val expected = ViewState.Success(response)
        Truth.assertThat(
            (formViewModel.onInvestmentData.getValueForTest())
        ).isEqualTo(expected)
    }

    @Test
    fun when_fetchInvestmentData_should_failure() {
        val exception = Exception("Test Exception")
        coEvery { repository.fetchInvestmentData(any()) } throws exception

        val formViewModel = FormViewModel(
            useCase = FetchInvestmentUseCase(repository),
            dispatcher = Dispatchers.Main
        )

        formViewModel.simulateInvestment()
        val expected = ViewState.Failure<Exception>(exception)
        Truth.assertThat(
            (formViewModel.onInvestmentData.getValueForTest())
        ).isEqualTo(expected)
    }

    @Test
    fun when_form_was_filled_formValidatorData_should_return_true() {
        val formViewModel = FormViewModel(
            useCase = FetchInvestmentUseCase(repository),
            dispatcher = Dispatchers.Main
        )

        formViewModel.amount = 100.0
        formViewModel.maturityDate = Date(0)
        formViewModel.rate = 99.3
        Truth.assertThat(formViewModel.formValidatorData.getValueForTest()).isTrue()
    }

    @Test
    fun when_form_not_was_filled_formValidatorData_should_return_false() {
        val formViewModel = FormViewModel(
            useCase = FetchInvestmentUseCase(repository),
            dispatcher = Dispatchers.Main
        )

        formViewModel.amount = null
        formViewModel.maturityDate = null
        formViewModel.rate = null
        Truth.assertThat(formViewModel.formValidatorData.getValueForTest()).isFalse()
    }
}
