package com.easynvest.data.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTypeDeserializer : JsonDeserializer<Date?> {
    companion object {
        val DATE_FORMATS = arrayOf("yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd")
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext?): Date? {
        DATE_FORMATS.iterator().forEach { format ->
            try {
                return SimpleDateFormat(format, Locale.getDefault()).parse(json.asString)
            } catch (e: ParseException) {
            }
        }
        return null
    }
}
