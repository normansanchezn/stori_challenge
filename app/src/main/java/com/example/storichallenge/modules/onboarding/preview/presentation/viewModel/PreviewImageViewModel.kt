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
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.domain.useCases.GetLocalAccountUseCase
import com.example.storichallenge.modules.onboarding.preview.domain.useCases.CreateUserAuthUseCase
import com.example.storichallenge.modules.onboarding.preview.domain.useCases.SaveRemoteAccountUseCase
import com.example.storichallenge.modules.onboarding.preview.domain.useCases.UpdateLocalIdPhotoUseCase
import com.example.storichallenge.modules.onboarding.utils.OnboardingConstants
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewImageViewModel @Inject constructor(
    private val getLocalAccountUseCase: GetLocalAccountUseCase,
    private val updateLocalIdPhotoUseCase: UpdateLocalIdPhotoUseCase,
    private val createUserAuthUseCase: CreateUserAuthUseCase,
    private val saveRemoteAccountUseCase: SaveRemoteAccountUseCase
): BaseViewModel() {

    private val imageUri: SingleLiveData<Uri> = SingleLiveData()
    private val imageBase64VM: SingleLiveData<String> = SingleLiveData()
    private val onUpdateLocalIdPhoto: SingleLiveData<Unit> = SingleLiveData()
    private val onCreateUser: SingleLiveData<Unit> = SingleLiveData()
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
        viewModelScope.launch {
            createUserAuthUseCase.invoke(localAccount?.email, localAccount?.password)
                .onStart { onShowLoadingMLD.safeSetValue(true) }
                .onCompletion { onShowLoadingMLD.safeSetValue(false) }
                .collect { result ->
                    when(result) {
                        FirebaseResult.FirebaseErrorOperation ->  {
                            onShowErrorMLD.safeSetValue("No se pudo crear tu cuenta, intentalo más tarde")
                        }
                        FirebaseResult.FirebaseSuccessOperation -> {
                            saveRemoteUser()
                        }
                    }
                }
        }
    }

    private fun saveRemoteUser() {
        viewModelScope.launch {
            saveRemoteAccountUseCase.invoke(localAccount)
                .onStart { onShowLoadingMLD.safeSetValue(true) }
                .onCompletion { onShowLoadingMLD.safeSetValue(false) }
                .collect { result ->
                    when(result) {
                        FirebaseResult.FirebaseErrorOperation ->  {
                            onShowErrorMLD.safeSetValue("No se pudo crear tu cuenta, intentalo más tarde")
                        }
                        FirebaseResult.FirebaseSuccessOperation -> {
                            onCreateUser.safeSetValue(Unit)
                        }
                    }
                }
        }
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
    fun onCreateUser(): LiveData<Unit> = onCreateUser

}