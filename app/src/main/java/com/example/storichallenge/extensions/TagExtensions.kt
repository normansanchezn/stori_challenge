package com.example.storichallenge.extensions

inline val <reified T> T.TAG: String
    get() = T::class.java.simpleName