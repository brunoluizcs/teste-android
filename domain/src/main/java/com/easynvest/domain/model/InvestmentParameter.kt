package com.easynvest.domain.model

import java.util.Date

data class InvestmentParameter(
    val investedAmount: Double,
    val yearlyInterestRate: Double,
    val maturityTotalDays: Int,
    val maturityBusinessDays: Int,
    val maturityDate: Date,
    val rate: Double,
    val isTaxFree: Boolean
)
