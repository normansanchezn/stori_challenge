package com.example.storichallenge.modules.account.data.dataSource.local

import com.example.storichallenge.data.database.local.entities.AccountEntity
import com.example.storichallenge.modules.account.data.model.RoomOperation

interface LocalAccountDataSource {

    suspend fun createAccount(email: String): RoomOperation
    suspend fun getAccount(): AccountEntity?

}