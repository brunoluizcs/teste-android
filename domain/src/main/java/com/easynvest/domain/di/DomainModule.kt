package com.easynvest.domain.di

import com.easynvest.domain.usecase.FetchInvestmentUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        FetchInvestmentUseCase(repository = get())
    }
}

val domainModule = listOf(useCaseModule)
