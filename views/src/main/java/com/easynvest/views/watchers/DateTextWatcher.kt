package com.easynvest.views.watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTextWatcher(private val editText: EditText) : TextWatcher {
    var onDateChanged: ((Date?)->Unit)? = null

    private var current: String? = null

    private fun clearDateValue(currencyValue: String?) =
        currencyValue?.replace(Regex("[/]"), "")

    override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, end: Int, count: Int) {
        if (s != current) {
            editText.removeTextChangedListener(this)
            if(end == 0) {
                val cleanString = clearDateValue(s.toString()) ?: ""
                val formatted = when (cleanString.length) {
                    2 -> "${cleanString.substring(0, 2)}/"
                    4 -> "${cleanString.substring(0, 2)}/${cleanString.substring(2, 4)}/"
                    else -> s.toString()
                }
                current = formatted
                editText.setText(formatted)
                editText.setSelection(formatted.length)
            }
            editText.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(value: Editable?) {
        try {
            value?.toString()?.let {
                onDateChanged?.invoke(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it))
            }
        }catch (e: ParseException) {
            onDateChanged?.invoke(null)
        }
    }
}