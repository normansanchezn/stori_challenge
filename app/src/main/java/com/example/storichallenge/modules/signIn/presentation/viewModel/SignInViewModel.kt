package com.example.storichallenge.modules.signIn.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.domain.useCases.LoginAccountUseCase
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginAccountUseCase: LoginAccountUseCase
): BaseViewModel() {

    private val onLogin: SingleLiveData<Unit> = SingleLiveData()

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

    fun onLogin(): LiveData<Unit> = onLogin

}