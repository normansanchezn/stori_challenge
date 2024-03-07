package com.example.storichallenge.modules.account.domain.useCases

import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(email: String, password: String): Flow<FirebaseResult> =
        repository.loginWithAccount(email, password)

}