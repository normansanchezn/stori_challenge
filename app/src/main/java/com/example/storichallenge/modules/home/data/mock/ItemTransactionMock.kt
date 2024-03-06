package com.example.storichallenge.modules.home.data.mock

import com.example.storichallenge.modules.home.data.model.TransactionItem

fun listMock() =
    listOf(
        TransactionItem(
            concept = "Transferencia",
            timestamp = "05 Marzo 2024",
            amount = 5000.0
        ),
        TransactionItem(
            concept = "Retiro",
            timestamp = "10 Febrero 2024",
            amount = 20.0
        ),
        TransactionItem(
            concept = "Transferencia",
            timestamp = "01 Enero 2024",
            amount = 230.0
        )
    )