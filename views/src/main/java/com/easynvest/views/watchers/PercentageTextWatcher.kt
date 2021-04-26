package com.easynvest.views.watchers

import android.text.Editable
import android.text.TextWatcher

class PercentageTextWatcher() : TextWatcher {
    var onPercentageChange: ((Double?) -> Unit)? = null

    override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, end: Int, count: Int) {}

    override fun afterTextChanged(value: Editable?) {
        try {
            onPercentageChange?.invoke(value?.toString()?.toDouble())
        } catch (e: NumberFormatException) {
            onPercentageChange?.invoke(null)
        }
    }
}
