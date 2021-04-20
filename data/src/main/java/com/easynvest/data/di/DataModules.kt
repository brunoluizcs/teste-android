package com.easynvest.data.di

import com.easynvest.data.repository.InvestmentRepositoryImp
import com.easynvest.domain.repository.InvestmentRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<InvestmentRepository> {
        InvestmentRepositoryImp(api = get())
    }
}

val dataModules = listOf(remoteDataSourceModule, repositoryModule)
