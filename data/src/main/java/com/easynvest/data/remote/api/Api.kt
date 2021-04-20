package com.easynvest.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("ecfaebf5-782b-4b24-ae4f-23b5c3a861da")
    suspend fun fetchInvestmentPrediction(
        @Query("investedAmount") investedAmount: Double,
        @Query("index") index: String,
        @Query("rate") rate: Double,
        @Query("maturityDate") maturityDate: String,
        @Query("isTaxFree") isTaxFree: Boolean
    ): String
}
