package com.easynvest.views.extensions

import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrency() : String = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    .format(this)
