package com.example.storichallenge.modules.onboarding.preview.domain.useCases

import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserAuthUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(email: String?, password: String?): Flow<FirebaseResult> =
        repository.createRemoteUserAuth(email, password)
}