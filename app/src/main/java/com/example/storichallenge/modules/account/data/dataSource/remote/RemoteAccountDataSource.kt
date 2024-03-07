package com.example.storichallenge.modules.account.data.dataSource.remote

import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.AccountBalance
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.model.ResultFirebase
import com.example.storichallenge.modules.home.data.model.TransactionItem
import kotlinx.coroutines.flow.Flow

interface RemoteAccountDataSource {

    suspend fun createRemoteUserAuth(email: String?, password: String?): Flow<FirebaseResult>
    suspend fun saveRemoteAccount(account: Account?): Flow<FirebaseResult>
    suspend fun loginWithAccount(email: String, password: String): Flow<FirebaseResult>
    suspend fun getTotalBalance(email: String?): Flow<ResultFirebase<AccountBalance>>
    suspend fun getListOfTransactions(email: String?): Flow<ResultFirebase<List<TransactionItem>>>

}