package com.example.storichallenge.utils

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storichallenge.R
import com.example.storichallenge.databinding.ViewStoriCurrencyBinding
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.utils.CurrencyCode.*
import java.text.NumberFormat
import java.util.Locale

class StoriCurrencyView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val colorBlack = resources.getColor(R.color.stori_black, null)
    private val colorGray = resources.getColor(R.color.gray_400, null)

    private val binding: ViewStoriCurrencyBinding = ViewStoriCurrencyBinding.inflate(
        LayoutInflater.from(context), this, true)

    private val _onTextAmountChanged = MutableLiveData<String>()
    val onTextAmountChanged: LiveData<String> get() = _onTextAmountChanged

    init {
        val value = context.obtainStyledAttributes(attrs, R.styleable.StoriCurrencyView)
        val sherpaEditable = value.getBoolean(R.styleable.StoriCurrencyView_storiEditable, true)
        val currencyKey = value.getInt(R.styleable.StoriCurrencyView_currencyKey, 0)
        value.recycle()
        binding.tipCurrency.text = getCurrencyKey(currencyKey)
        binding.currency.isEnabled = sherpaEditable
        if (sherpaEditable) {
            binding.currency.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.sp_70))
            binding.tipCurrency.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.sp_30))
        } else {
            binding.currency.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.sp_37))
            binding.tipCurrency.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.sp_22))
            val params: MarginLayoutParams = binding.tipCurrency.layoutParams as MarginLayoutParams
            params.leftMargin = 16
        }

        initListeners()
    }

    private val textWatcher: CurrencyInputWatcher by lazy {
        CurrencyInputWatcher(binding.currency) { text ->
            binding.tipCurrency.setTextColor(if (text.isEmpty()) colorGray else colorBlack)
            _onTextAmountChanged.safeSetValue(getAmount().toString())
        }
    }

    fun getAmount(): Double {
        return parseMoneyValueWithLocale(
            binding.currency.text.toString(),
            textWatcher.decimalFormatSymbols.groupingSeparator.toString(),
            "$"
        ).toDouble()
    }

    private fun initListeners() {
        binding.currency.addTextChangedListener(textWatcher)
    }

    private fun getCurrencyKey(currencyKey: Int): String {
        return when (currencyKey) {
            MXN.int -> MXN.name
            USD.int -> USD.name
            else -> MXN.name
        }
    }

    fun setAmount(amount: Double) {
        val format = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
        if (amount % 1 == 0.0) {
            format.maximumFractionDigits = 0
        } else {
            format.maximumFractionDigits = 2
        }
        binding.currency.setTextColor(colorBlack)
        binding.tipCurrency.setTextColor(colorBlack)
        binding.currency.removeTextChangedListener(textWatcher)
        binding.currency.setText(format.format(amount))
        binding.currency.addTextChangedListener(textWatcher)
    }

    fun setTextSize(size: Float) {
        binding.currency.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    fun setHint(hint: String) {
        binding.currency.hint = hint
    }

}