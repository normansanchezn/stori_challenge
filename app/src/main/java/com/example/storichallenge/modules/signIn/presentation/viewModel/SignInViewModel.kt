package com.example.storichallenge.modules.signIn.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.model.ResultFirebase
import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.domain.useCases.CreateAccountUseCase
import com.example.storichallenge.modules.account.domain.useCases.CreateLocalAccountUseCase
import com.example.storichallenge.modules.account.domain.useCases.GetRemoteAccountUseCase
import com.example.storichallenge.modules.account.domain.useCases.LoginAccountUseCase
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginAccountUseCase: LoginAccountUseCase,
    private val getRemoteAccountUseCase: GetRemoteAccountUseCase,
    private val createLocalAccountUseCase: CreateLocalAccountUseCase
): BaseViewModel() {

    private val onLogin: SingleLiveData<Unit> = SingleLiveData()
    private val onGetRemoteAccount: SingleLiveData<Unit> = SingleLiveData()

    fun loginWithCredentials(email: String, password: String) {
        viewModelScope.launch {
            loginAccountUseCase.invoke(email, password)
                .onStart { onShowLoadingMLD.safeSetValue(true) }
                .onCompletion { onShowLoadingMLD.safeSetValue(false) }
                .collect { result ->
                    when(result) {
                        FirebaseResult.FirebaseErrorOperation ->  {
                            onShowErrorMLD.safeSetValue("No se pudo iniciar sesión, valida tus datos")
                        }
                        FirebaseResult.FirebaseSuccessOperation -> {
                            onLogin.safeSetValue(Unit)
                        }
                    }
                }
        }
    }

    fun getRemoteAccount(email: String) {
        viewModelScope.launch {
            getRemoteAccountUseCase.invoke(email)
                .collect { result ->
                    when(result) {
                        is ResultFirebase.Error -> {
                            onShowErrorMLD.safeSetValue("Tuvimos un problema para obtener tu información, inténtalo más tarde")
                        }
                        is ResultFirebase.Success -> {
                            createLocalAccountWithRemoteData(result.data)
                        }
                    }
                }
        }
    }

    private fun createLocalAccountWithRemoteData(account: Account?) {
        viewModelScope.launch {
            account?.let {
                when(createLocalAccountUseCase.invoke(account)) {
                    RoomOperation.ErrorOperation -> {
                        onShowErrorMLD.safeSetValue("Tuvimos un problema para obtener tu información, inténtalo más tarde")
                    }
                    RoomOperation.SuccessOperation -> {
                        onGetRemoteAccount.safeSetValue(Unit)
                    }
                }
            }
        }
    }

    fun onLogin(): LiveData<Unit> = onLogin
    fun onGetRemoteAccount(): LiveData<Unit> = onGetRemoteAccount

}