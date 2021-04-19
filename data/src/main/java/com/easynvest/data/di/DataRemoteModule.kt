package com.easynvest.data.di

import com.easynvest.data.remote.api.Api
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val remoteDataSourceModule = module {
    factory { providesOkHttpClient() }
    single {
        createWebService<Api>(
            okHttpClient = get(),
            url = "https://run.mocky.io/v3/"
        )
    }
}

fun providesOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    url: String
): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .build()
        .create(T::class.java)
}