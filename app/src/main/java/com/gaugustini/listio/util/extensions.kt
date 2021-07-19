package com.gaugustini.listio.util

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(): String {
    val timeZone = TimeZone.getDefault()
    val offset = timeZone.getOffset(Date().time) * -1
    return Date(this + offset).format()
}

fun Date.format(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)
}

var TextInputLayout.text: String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }

