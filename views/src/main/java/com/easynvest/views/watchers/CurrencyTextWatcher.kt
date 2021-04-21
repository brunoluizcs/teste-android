package com.easynvest.views.watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale


class CurrencyTextWatcher(private val editText: EditText): TextWatcher {
    var onCurrencyChanged: ((Double?)->Unit)? = null

    private var current: String? = null

    private fun clearCurrencyToNumber(currencyValue: String?) =
        currencyValue?.replace(Regex("[(a-z)|(A-Z)|($,.\\s\\t)]"), "")

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, end: Int, count: Int) {
        if (s != current) {
            editText.removeTextChangedListener(this)
            val cleanString = clearCurrencyToNumber(s?.toString()) ?: "0.0"
            val parsed = cleanString.toDouble()
            val formatted = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                .format(parsed / 100)
            current = formatted
            editText.setText(formatted)
            editText.setSelection(formatted.length)
            editText.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(value: Editable?) {
        try {
            val clearNumber = clearCurrencyToNumber(value?.toString())?.toDouble()
            onCurrencyChanged?.invoke(clearNumber?.div(100))
        }catch (e: NumberFormatException){
            onCurrencyChanged?.invoke(null)
        }
    }


}