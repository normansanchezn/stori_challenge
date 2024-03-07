package com.example.storichallenge.modules.onboarding.preview.presentation.viewModel

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.parcelable
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.domain.useCases.GetLocalAccountUseCase
import com.example.storichallenge.modules.onboarding.preview.domain.useCases.UpdateLocalIdPhotoUseCase
import com.example.storichallenge.modules.onboarding.utils.OnboardingConstants
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewImageViewModel @Inject constructor(
    private val getLocalAccountUseCase: GetLocalAccountUseCase,
    private val updateLocalIdPhotoUseCase: UpdateLocalIdPhotoUseCase

): BaseViewModel() {

    private val imageUri: SingleLiveData<Uri> = SingleLiveData()
    private val imageBase64VM: SingleLiveData<String> = SingleLiveData()
    private val onUpdateLocalIdPhoto: SingleLiveData<Unit> = SingleLiveData()
    private var localAccount: Account?= null

    init {
        getLocalAccount()
    }

    private fun getLocalAccount() {
        viewModelScope.launch {
            val localAccountDeferred = async { getLocalAccountUseCase.invoke() }
            localAccount = localAccountDeferred.await()
        }
    }

    override fun setup(arguments: Bundle?) {
        arguments?.let {
            imageUri.safeSetValue(arguments.parcelable(OnboardingConstants.BUNDLE_URI_PHOTO))
        }
    }

    fun holdImage(imageBase64: String) {
        imageBase64VM.safeSetValue(imageBase64)
    }

    fun updateLocalIdPhoto() {
        viewModelScope.launch {
            when(updateLocalIdPhotoUseCase.invoke(
                localAccount?.email,
                imageBase64VM.value
            )) {
                RoomOperation.ErrorOperation -> {
                    onShowErrorMLD.safeSetValue(
                        "No se pudo guardar su información, inténtelo más tarde."
                    )
                }
                RoomOperation.SuccessOperation -> {
                    onUpdateLocalIdPhoto.safeSetValue(Unit)
                }
            }
        }
    }

    fun createRemoteUser() {

    }

    fun navigateToSuccessView() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_previewImageFragment_to_successFragment
            )
        )
    }

    fun onGetImageUri(): LiveData<Uri> = imageUri
    fun onUpdateLocalIdPhoto(): LiveData<Unit> = onUpdateLocalIdPhoto

}