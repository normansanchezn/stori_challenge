package com.example.storichallenge.modules.account.domain.useCases

import com.example.storichallenge.modules.account.data.mappers.AccountMapper
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import javax.inject.Inject

class GetLocalAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(): Account? {
        val accountEntity = repository.getLocalAccount()
        return AccountMapper().map(accountEntity)
    }

}