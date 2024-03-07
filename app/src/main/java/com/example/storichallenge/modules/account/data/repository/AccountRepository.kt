package com.example.storichallenge.modules.account.data.repository

import com.example.storichallenge.modules.account.data.dataSource.local.LocalAccountDataSource
import com.example.storichallenge.modules.account.data.dataSource.remote.RemoteAccountDataSource
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.model.RoomOperation
import kotlinx.coroutines.flow.Flow
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

    suspend fun createRemoteUserAuth(email: String?, password: String?): Flow<FirebaseResult> =
        remoteAccountDataSource.createRemoteUserAuth(email, password)

    suspend fun saveRemoteAccount(account: Account?): Flow<FirebaseResult> =
        remoteAccountDataSource.saveRemoteAccount(account)

    suspend fun loginWithAccount(email: String, password: String): Flow<FirebaseResult> =
        remoteAccountDataSource.loginWithAccount(email, password)

}