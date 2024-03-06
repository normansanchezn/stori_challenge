package com.example.storichallenge.modules.onboarding.passwordView.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.domain.useCases.GetLocalAccountUseCase
import com.example.storichallenge.modules.onboarding.personalData.domain.useCases.SaveLocalPasswordUseCase
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val getLocalAccountUseCase: GetLocalAccountUseCase,
    private val saveLocalPasswordUseCase: SaveLocalPasswordUseCase
): BaseViewModel() {

    private var localAccount: Account ?= null
    private val onSaveLocalPassword: SingleLiveData<Unit> = SingleLiveData()

    init {
        getLocalAccount()
    }

    private fun getLocalAccount() {
        viewModelScope.launch {
            val localAccountDeferred = async { getLocalAccountUseCase.invoke() }
            localAccount = localAccountDeferred.await()
        }
    }

    fun saveLocalPassword(password: String) {
        viewModelScope.launch {
            when (saveLocalPasswordUseCase.invoke(
                localAccount?.email,
                password)
            ) {
                RoomOperation.ErrorOperation -> {
                    onShowErrorMLD.safeSetValue("No se pudo guardar su información, inténtelo más tarde.")
                }
                RoomOperation.SuccessOperation -> {
                    onSaveLocalPassword.safeSetValue(Unit)
                }
            }
        }
    }

    fun navigateToCameraUsageWarning() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_passwordFragment_to_cameraUsageWarningFragment
            )
        )
    }

    fun onSaveLocalPassword(): LiveData<Unit> = onSaveLocalPassword
}