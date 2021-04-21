package com.easynvest.investments.di

import com.easynvest.investments.features.form.FormViewModel
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<FormViewModel> {
        FormViewModel(useCase = get(), dispatcher = Dispatchers.IO)
    }

    factory<Sprite> {
        DoubleBounce()
    }
}
