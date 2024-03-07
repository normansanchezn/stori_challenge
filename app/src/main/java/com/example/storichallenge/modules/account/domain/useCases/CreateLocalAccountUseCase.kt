package com.example.storichallenge.modules.account.domain.useCases

import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import javax.inject.Inject

class CreateLocalAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(account: Account): RoomOperation =
        repository.createLocalAccountWithRemoteData(account)

}