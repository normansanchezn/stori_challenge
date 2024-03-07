package com.example.storichallenge.modules.account.data.dataSource.remote

import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import kotlinx.coroutines.flow.Flow

interface RemoteAccountDataSource {

    suspend fun createRemoteUserAuth(email: String?, password: String?): Flow<FirebaseResult>
    suspend fun saveRemoteAccount(account: Account?): Flow<FirebaseResult>
    suspend fun loginWithAccount(email: String, password: String): Flow<FirebaseResult>

}