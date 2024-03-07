package com.example.storichallenge.modules.account.domain.useCases

import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.ResultFirebase
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemoteAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(email: String): Flow<ResultFirebase<Account>> =
        repository.getRemoteAccount(email)

}