package com.example.storichallenge.modules.account.data.repository

import com.example.storichallenge.modules.account.data.dataSource.local.LocalAccountDataSource
import com.example.storichallenge.modules.account.data.dataSource.remote.RemoteAccountDataSource
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.RoomOperation
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val localAccountDataSource: LocalAccountDataSource,
    private val remoteAccountDataSource: RemoteAccountDataSource
) {

    suspend fun createLocalAccount(email: String): RoomOperation =
        localAccountDataSource.createAccount(email)

    suspend fun getLocalAccount() =
        localAccountDataSource.getAccount()

    suspend fun updatePersonalData(email: String?, name: String, lastName: String) =
        localAccountDataSource.updatePersonalData(email, name, lastName)

    suspend fun updateLocalPassword(email: String?, password: String) =
        localAccountDataSource.updateLocalPassword(email, password)

    suspend fun updateLocalIdPhoto(email: String?, idPhotoBase64: String?) =
        localAccountDataSource.updateLocalIdPhotoBase64(email, idPhotoBase64)

    suspend fun createRemoteUserAuth(email: String?, password: String?) =
        remoteAccountDataSource.createRemoteUserAuth(email, password)

    suspend fun saveRemoteAccount(account: Account?) =
        remoteAccountDataSource.saveRemoteAccount(account)

}