package com.example.storichallenge.modules.account.data.repository

import androidx.room.Room
import com.example.storichallenge.modules.account.data.dataSource.local.LocalAccountDataSource
import com.example.storichallenge.modules.account.data.dataSource.remote.RemoteAccountDataSource
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.AccountBalance
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.model.ResultFirebase
import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.home.data.model.TransactionItem
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

    suspend fun getTotalBalance(email: String?): Flow<ResultFirebase<AccountBalance>> =
        remoteAccountDataSource.getTotalBalance(email)

    suspend fun getTransactionHistory(email: String?): Flow<ResultFirebase<List<TransactionItem>>> =
        remoteAccountDataSource.getListOfTransactions(email)

    suspend fun deleteAccount(): RoomOperation = localAccountDataSource.deleteAccount()

    suspend fun signOutFromFirebase(): Flow<FirebaseResult> =
        remoteAccountDataSource.signOutFromFirebase()
}