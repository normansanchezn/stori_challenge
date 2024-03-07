package com.example.storichallenge.modules.home.home.domain.useCases

import com.example.storichallenge.modules.account.data.model.AccountBalance
import com.example.storichallenge.modules.account.data.model.ResultFirebase
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalBalanceUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(email: String): Flow<ResultFirebase<AccountBalance>> =
        repository.getTotalBalance(email)

}