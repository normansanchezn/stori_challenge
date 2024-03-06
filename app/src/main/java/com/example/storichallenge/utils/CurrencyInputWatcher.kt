package com.example.storichallenge.utils

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.storichallenge.constants.StoriConstants.EMPTY_STRING
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
import java.util.*
import kotlin.math.min

private const val currencySymbol = "$"
private const val maxNumberOfDecimalPlaces = 2

class CurrencyInputWatcher(private val editText: EditText,
private val onTextChanged:(String) -> Unit) : TextWatcher {

    init {
        if (maxNumberOfDecimalPlaces < 1) {
            throw IllegalArgumentException("Maximum number of Decimal Digits must be a positive integer")
        }
    }

    companion object {
        const val FRACTION_FORMAT_PATTERN_PREFIX = "#,##0."
    }

    private var hasDecimalPoint = false
    private val wholeNumberDecimalFormat =
        (NumberFormat.getNumberInstance(Locale("es", "MX")) as DecimalFormat).apply {
            applyPattern("#,##0")
        }

    private val fractionDecimalFormat = (NumberFormat.getNumberInstance(Locale("es", "MX")) as DecimalFormat)

    val decimalFormatSymbols: DecimalFormatSymbols
        get() = wholeNumberDecimalFormat.decimalFormatSymbols

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        fractionDecimalFormat.isDecimalSeparatorAlwaysShown = true
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        hasDecimalPoint = s.toString().contains(decimalFormatSymbols.decimalSeparator.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun afterTextChanged(s: Editable) {
        val newInputString = s.toString()
        onTextChanged.invoke(newInputString)
        val isParsableString = try {
            fractionDecimalFormat.parse(newInputString)!!
            true
        } catch (e: ParseException) {
            false
        }

        if (newInputString.length < currencySymbol.length && !isParsableString) {
//            editText.setText(currencySymbol)
//            editText.setSelection(currencySymbol.length)
            editText.hint = "$0.00"
            return
        }
        editText.hint = "$0"

        if (newInputString == currencySymbol) {
            editText.setText(EMPTY_STRING)
            return
        }

        editText.removeTextChangedListener(this)
        //val startLength = editText.text.length
        try {
            var numberWithoutGroupingSeparator =
                parseMoneyValue(
                    newInputString,
                    decimalFormatSymbols.groupingSeparator.toString(),
                    currencySymbol
                )
            if (numberWithoutGroupingSeparator == decimalFormatSymbols.decimalSeparator.toString()) {
                numberWithoutGroupingSeparator = "0$numberWithoutGroupingSeparator"
            }

            numberWithoutGroupingSeparator = truncateNumberToMaxDecimalDigits(
                numberWithoutGroupingSeparator,
                maxNumberOfDecimalPlaces,
                decimalFormatSymbols.decimalSeparator
            )

            val parsedNumber = fractionDecimalFormat.parse(numberWithoutGroupingSeparator)!!
            //val selectionStartIndex = editText.selectionStart
            if (hasDecimalPoint) {
                fractionDecimalFormat.applyPattern(
                    FRACTION_FORMAT_PATTERN_PREFIX +
                            getFormatSequenceAfterDecimalSeparator(numberWithoutGroupingSeparator)
                )
                editText.setText("$currencySymbol${fractionDecimalFormat.format(parsedNumber)}")
            } else {
                editText.setText("$currencySymbol${wholeNumberDecimalFormat.format(parsedNumber)}")
            }
            //val endLength = editText.text.length
            //val selection = selectionStartIndex + (endLength - startLength)
            /*if (selection > 0 && selection <= editText.text.length) {
                editText.setSelection(selection)
            } else {
                editText.setSelection(editText.text.length - 1)
            }*/
            editText.setSelection(editText.text.length)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        editText.addTextChangedListener(this)
    }

    /**
     * @param number the original number to format
     * @return the appropriate zero sequence for the input number. e.g 156.1 returns "0",
     *  14.98 returns "00"
     */
    private fun getFormatSequenceAfterDecimalSeparator(number: String): String {
        val noOfCharactersAfterDecimalPoint = number.length - number.indexOf(decimalFormatSymbols.decimalSeparator) - 1
        return "0".repeat(min(noOfCharactersAfterDecimalPoint, maxNumberOfDecimalPlaces))
    }

    private fun truncateNumberToMaxDecimalDigits(
        number: String,
        maxDecimalDigits: Int,
        decimalSeparator: Char
    ): String {
        // Split number into whole and decimal part
        val arr = number
            .split(decimalSeparator)
            .toMutableList()

        // We should have exactly 2 elements in our string;
        // the whole part and the decimal part
        if (arr.size != 2) {
            return number
        }

        // Take the first n (or shorter) from the decimal digits.
        arr[1] = arr[1].take(maxDecimalDigits)

        return arr.joinToString(separator = decimalSeparator.toString())
    }

}

internal fun parseMoneyValue(
    value: String,
    groupingSeparator: String,
    currencySymbol: String
): String =
    value.replace(groupingSeparator, EMPTY_STRING).replace(currencySymbol, EMPTY_STRING)

internal fun parseMoneyValueWithLocale(
    value: String,
    groupingSeparator: String,
    currencySymbol: String
): Number {

    val valueWithoutSeparator = parseMoneyValue(value, groupingSeparator, currencySymbol)
    return try {
        NumberFormat.getInstance(Locale("es", "MX")).parse(valueWithoutSeparator)!!
    } catch (exception: ParseException) {
        0
    }
}