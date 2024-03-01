package com.example.storichallenge.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.safeSetValue(t: T?) {
    try {
        value = t
    } catch (e: IllegalStateException) {
        postValue(t)
    }
}