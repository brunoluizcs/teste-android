package com.easynvest.domain.model

import java.util.Date

data class InvestmentRequest(
    val investedAmount: Double,
    val index: String,
    val rate: Double,
    val isTaxFree: Boolean,
    val maturityDate: Date
)
