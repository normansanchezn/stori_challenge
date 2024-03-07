package com.example.storichallenge.modules.home.data.model

import androidx.annotation.Keep

@Keep
data class TransactionItem(
    val concept: String?,
    val timestamp: String?,
    val amount: Double?
)
