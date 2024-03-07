package com.example.storichallenge.modules.home.home.domain.useCases

import com.example.storichallenge.modules.account.data.model.ResultFirebase
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import com.example.storichallenge.modules.home.data.model.TransactionItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionHistoryUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(email: String): Flow<ResultFirebase<List<TransactionItem>>> =
        repository.getTransactionHistory(email)


}