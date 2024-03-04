package com.example.storichallenge.extensions

import android.view.View

const val DEFAULT_DEBOUNCE_TIME = 2000L

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

@JvmOverloads
fun View.debounceClick(debounceTime: Long = DEFAULT_DEBOUNCE_TIME, onClickAction: () -> Unit) {
    var isClickEnable = true
    setOnClickListener {
        if (isClickEnable) {
            isClickEnable = false
            onClickAction()
            postDelayed({ isClickEnable = true }, debounceTime)
        }
    }
}