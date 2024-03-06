package com.example.storichallenge.modules.account.domain.useCases

import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend fun invoke(email: String): RoomOperation =
        accountRepository.createAccount(email)
}