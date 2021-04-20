package com.easynvest.investments

import android.app.Application
import com.easynvest.data.di.dataModules
import com.easynvest.domain.di.domainModule
import com.easynvest.investments.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(domainModule + dataModules + listOf(presentationModule))
        }
    }
}
