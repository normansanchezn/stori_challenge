package com.example.storichallenge.modules.unlock.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.domain.useCases.GetLocalAccountUseCase
import com.example.storichallenge.modules.account.domain.useCases.LoginAccountUseCase
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnlockViewModel @Inject constructor(
    private val getLocalAccountUseCase: GetLocalAccountUseCase,
    private val loginAccountUseCase: LoginAccountUseCase
): BaseViewModel() {

    private var localAccount: Account ?= null
    private val onLogin: SingleLiveData<Unit> = SingleLiveData()

    init {
        getLocalAccount()
    }

    private fun getLocalAccount() {
        viewModelScope.launch {
            val localAccountDeferred = async { getLocalAccountUseCase.invoke() }
            localAccount = localAccountDeferred.await()
        }
    }

    fun navigateToLoginView() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_unlockFragment_to_signInFragment
            )
        )
    }

    fun loginWithUser() {
        viewModelScope.launch {
            loginAccountUseCase.invoke(localAccount?.email!!, localAccount?.password!!)
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