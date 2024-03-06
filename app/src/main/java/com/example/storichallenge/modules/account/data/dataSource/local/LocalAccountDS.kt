package com.example.storichallenge.modules.account.data.dataSource.local

import android.util.Log
import com.example.storichallenge.data.database.local.dao.AccountDao
import com.example.storichallenge.data.database.local.entities.AccountEntity
import com.example.storichallenge.extensions.TAG
import com.example.storichallenge.modules.account.data.model.RoomOperation
import javax.inject.Inject
import kotlin.reflect.typeOf

class LocalAccountDS @Inject constructor(
    private val localDataSource: AccountDao
): LocalAccountDataSource {
    override suspend fun createAccount(email: String): RoomOperation =
        try {
            localDataSource.createAccount(AccountEntity(
                email = email,
                null,
                null,
                null,
                null
            ))
            RoomOperation.SuccessOperation
        } catch (e: Exception) {
            Log.i(TAG, "createAccount: ${e.message}")
            RoomOperation.ErrorOperation
        }

    override suspend fun getAccount(): AccountEntity? =
        localDataSource.getAccount()

    override suspend fun updatePersonalData(
        email: String?,
        name: String,
        lastName: String
    ): RoomOperation =
        try {
            localDataSource.updateNameAndLastName(email, name, lastName)
            RoomOperation.SuccessOperation
        } catch (e: Exception) {
            Log.i(TAG, "updatePersonalData: ${e.message}")
            RoomOperation.ErrorOperation
        }

    override suspend fun updateLocalPassword(
        email: String?,
        password: String
    ): RoomOperation =
        try {
            localDataSource.updatePassword(email, password)
            RoomOperation.SuccessOperation
        } catch (e: Exception) {
            Log.i(TAG, "updateLocalPassword: ${e.message}")
            RoomOperation.ErrorOperation
        }
}