package com.example.storichallenge.modules.onboarding.preview.domain.useCases

import com.example.storichallenge.modules.account.data.repository.AccountRepository
import javax.inject.Inject

class UpdateLocalIdPhotoUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun invoke(email: String?, idPhotoBase64: String?) =
        repository.updateLocalIdPhoto(email, idPhotoBase64)

}