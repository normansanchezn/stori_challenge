package com.example.storichallenge.modules.profile.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.domain.useCases.GetLocalAccountUseCase
import com.example.storichallenge.modules.profile.domain.useCases.DeleteLocalAccountUseCase
import com.example.storichallenge.modules.profile.domain.useCases.SignOutFromFirebaseUseCase
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getLocalAccountUseCase: GetLocalAccountUseCase,
    private val deleteAccountUseCase: DeleteLocalAccountUseCase,
    private val signOutFromFirebaseUseCase: SignOutFromFirebaseUseCase
): BaseViewModel() {

    private val onGetAccount : SingleLiveData<Account> = SingleLiveData()
    private val onSignOut : SingleLiveData<Unit> = SingleLiveData()

    fun getAccount() {
        viewModelScope.launch {
            val localAccountDeferred = async { getLocalAccountUseCase.invoke() }
            onGetAccount.safeSetValue(localAccountDeferred.await())
        }
    }

    fun signOut() {
        viewModelScope.launch {
            when(deleteAccountUseCase.invoke()) {
                RoomOperation.ErrorOperation -> {
                    onShowErrorMLD.safeSetValue("No se pudo cerrar sesión, inténtelo mas tarde")
                }
                RoomOperation.SuccessOperation -> {
                    signOutFromFirebase()
                }
            }
        }
    }

    private fun signOutFromFirebase() {
        viewModelScope.launch {
            signOutFromFirebaseUseCase.invoke()
                .collect { result ->
                    when(result) {
                        FirebaseResult.FirebaseErrorOperation -> {
                            onShowErrorMLD.safeSetValue("No se pudo cerrar sesión, inténtelo mas tarde")
                        }
                        FirebaseResult.FirebaseSuccessOperation -> {
                            onSignOut.safeSetValue(Unit)
                        }
                    }
                }
        }
    }

    fun onGetAccount(): LiveData<Account> = onGetAccount
    fun onSignOut(): LiveData<Unit> = onSignOut
}