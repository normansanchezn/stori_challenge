package com.example.storichallenge.base.model

import com.google.errorprone.annotations.Keep

@Keep
data class DialogTexts(
    val title: String? = null,
    val message: CharSequence? = null,
    val primaryButtonText: String? = null,
    val secondaryButtonText: String? = null
)