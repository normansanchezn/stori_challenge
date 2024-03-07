package com.example.storichallenge.modules.profile.domain.useCases

import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import javax.inject.Inject

class DeleteLocalAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(): RoomOperation =
        repository.deleteAccount()
}