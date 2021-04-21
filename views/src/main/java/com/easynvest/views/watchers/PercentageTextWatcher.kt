package com.easynvest.views.watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PercentageTextWatcher() : TextWatcher {
    var onPercentageChange: ((Double?)->Unit)? = null

    override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, end: Int, count: Int) {}

    override fun afterTextChanged(value: Editable?) {
        try {
            onPercentageChange?.invoke(value?.toString()?.toDouble())
        }catch (e: NumberFormatException){
            onPercentageChange?.invoke(null)
        }
    }
}