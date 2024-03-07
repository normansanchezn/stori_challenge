package com.example.storichallenge.extensions

fun String.formatCurrency(): String {
    val reversedInput = this.reversed()
    val formattedNumber = StringBuilder()

    for ((index, char) in reversedInput.withIndex()) {
        formattedNumber.append(char)
        if ((index + 1) % 3 == 0 && (index + 1) != reversedInput.length) {
            formattedNumber.append(",")
        }
    }

    return "$${formattedNumber.reverse()}"
}