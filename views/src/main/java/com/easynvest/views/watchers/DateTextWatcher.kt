package com.easynvest.views.watchers

import android.text.Editable
import android.text.TextWatcher
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTextWatcher : TextWatcher {
    var onDateChanged: ((Date?)->Unit)? = null

    override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(value: Editable?) {
        try {
            value?.toString()?.let {
                onDateChanged?.invoke(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it))
            }
        }catch (e: ParseException) {

        }
    }
}