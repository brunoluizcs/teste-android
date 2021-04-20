package com.easynvest.views.watchers

import android.text.Editable
import android.text.TextWatcher
import java.lang.NumberFormatException


class CurrencyTextWatcher: TextWatcher {
    var onCurrencyChanged: ((Double?)->Unit)? = null

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(value: Editable?) {
        try {
             onCurrencyChanged?.invoke(value?.toString()?.toDouble())
        }catch (e: NumberFormatException){}
    }
}