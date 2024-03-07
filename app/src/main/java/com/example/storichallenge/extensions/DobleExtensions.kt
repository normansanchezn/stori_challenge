package com.example.storichallenge.extensions

import java.text.NumberFormat
import java.util.Locale

fun Double.toMoneyFormat(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
    format.maximumFractionDigits = 2
    return if (this == null) "${format.format(0.0)}" else "${format.format(this)}"
}