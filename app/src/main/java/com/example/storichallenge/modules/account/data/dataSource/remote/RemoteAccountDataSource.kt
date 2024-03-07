package com.example.storichallenge.modules.account.data.dataSource.remote

import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.FirebaseResult

interface RemoteAccountDataSource {

    suspend fun createRemoteUserAuth(email: String?, password: String?): FirebaseResult

    suspend fun saveRemoteAccount(account: Account?): FirebaseResult

}