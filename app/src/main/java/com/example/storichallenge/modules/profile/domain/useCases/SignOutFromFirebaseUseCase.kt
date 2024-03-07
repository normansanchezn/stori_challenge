package com.example.storichallenge.modules.profile.domain.useCases

import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignOutFromFirebaseUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(): Flow<FirebaseResult> =
        repository.signOutFromFirebase()

}