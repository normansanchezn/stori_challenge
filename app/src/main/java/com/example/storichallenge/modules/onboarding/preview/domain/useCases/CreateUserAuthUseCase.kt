package com.example.storichallenge.modules.onboarding.preview.domain.useCases

import com.example.storichallenge.modules.account.data.repository.AccountRepository
import javax.inject.Inject

class CreateUserAuthUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(email: String?, password: String?) =
        repository.createRemoteUserAuth(email, password)
}