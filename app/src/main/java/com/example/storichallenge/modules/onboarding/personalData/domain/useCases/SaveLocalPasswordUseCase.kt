package com.example.storichallenge.modules.onboarding.personalData.domain.useCases

import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import javax.inject.Inject

class SaveLocalPasswordUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(email: String?, password: String): RoomOperation =
        repository.updateLocalPassword(email, password)

}