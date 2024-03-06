package com.example.storichallenge.modules.account.data.repository

import com.example.storichallenge.modules.account.data.dataSource.local.LocalAccountDataSource
import com.example.storichallenge.modules.account.data.model.RoomOperation
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val localAccountDataSource: LocalAccountDataSource
) {

    suspend fun createLocalAccount(email: String): RoomOperation =
        localAccountDataSource.createAccount(email)

    suspend fun getLocalAccount() =
        localAccountDataSource.getAccount()

    suspend fun updatePersonalData(email: String?, name: String, lastName: String) =
        localAccountDataSource.updatePersonalData(email, name, lastName)

    suspend fun updateLocalPassword(email: String?, password: String) =
        localAccountDataSource.updateLocalPassword(email, password)

}