package com.example.storichallenge.modules.onboarding.email.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.modules.account.domain.useCases.CreateAccountUseCase
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private var createAccountUseCase: CreateAccountUseCase
): BaseViewModel() {

    private val onCreateAccount: SingleLiveData<Unit> = SingleLiveData()

    fun navigateToPersonalInformation() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_emailFragment_to_personalDataFragment
            )
        )
    }

    fun saveLocalEmailAddress(email: String) {
        viewModelScope.launch {
            when(createAccountUseCase.invoke(email)) {
                RoomOperation.SuccessOperation -> {
                    onCreateAccount.safeSetValue(Unit)
                }
                RoomOperation.ErrorOperation -> {
                    onShowErrorMLD.safeSetValue(
                        "No se pudo guardar su información, inténtelo más tarde."
                    )
                }
            }
        }
    }

    fun onCreateAccount(): LiveData<Unit> = onCreateAccount
}