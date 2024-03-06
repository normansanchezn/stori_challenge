package com.example.storichallenge.modules.account.data.repository

import com.example.storichallenge.modules.account.data.dataSource.local.LocalAccountDataSource
import com.example.storichallenge.modules.account.data.model.RoomOperation
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val localAccountDataSource: LocalAccountDataSource
) {

    suspend fun createAccount(email: String): RoomOperation =
        localAccountDataSource.createAccount(email)

}