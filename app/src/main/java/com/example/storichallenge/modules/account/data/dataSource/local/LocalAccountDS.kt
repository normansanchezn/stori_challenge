package com.example.storichallenge.modules.account.data.dataSource.local

import android.util.Log
import androidx.room.Room
import com.example.storichallenge.data.database.local.dao.AccountDao
import com.example.storichallenge.data.database.local.entities.AccountEntity
import com.example.storichallenge.extensions.TAG
import com.example.storichallenge.modules.account.data.model.Account
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

    override suspend fun updateLocalIdPhotoBase64(
        email: String?,
        idPhotoBase64: String?
    ): RoomOperation =
        try {
            localDataSource.updateIdPhoto(email, idPhotoBase64)
            RoomOperation.SuccessOperation
        } catch (e: Exception) {
            Log.i(TAG, "updateLocalPassword: ${e.message}")
            RoomOperation.ErrorOperation
        }

    override suspend fun deleteAccount(): RoomOperation =
        try {
            localDataSource.delete()
            RoomOperation.SuccessOperation
        } catch (e: Exception) {
            RoomOperation.ErrorOperation
        }

    override suspend fun createLocalAccountWithRemoteData(account: Account): RoomOperation =
        try {
            localDataSource.createAccount(
                AccountEntity(
                    email = account.email,
                    name = account.name,
                    password = account.password,
                    idPhoto = account.idPhoto,
                    lastName = account.lastName
                )
            )
            RoomOperation.SuccessOperation
        }catch (e: Exception) {
            RoomOperation.ErrorOperation
        }
}