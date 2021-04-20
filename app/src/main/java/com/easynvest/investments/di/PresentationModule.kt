package com.easynvest.investments.di

import com.easynvest.investments.features.form.FormViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<FormViewModel> {
        FormViewModel(useCase = get(), dispatcher = Dispatchers.IO)
    }
}
