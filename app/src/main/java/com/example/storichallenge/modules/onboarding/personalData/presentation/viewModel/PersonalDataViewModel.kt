package com.example.storichallenge.modules.onboarding.personalData.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.RoomOperation
import com.example.storichallenge.modules.account.domain.useCases.GetLocalAccountUseCase
import com.example.storichallenge.modules.onboarding.personalData.domain.useCases.UpdatePersonalDataUseCase
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val getLocalAccountUseCase: GetLocalAccountUseCase,
    private val updatePersonalDataUseCase: UpdatePersonalDataUseCase
): BaseViewModel() {

    private var localAccount: Account? = null
    private val onUpdatePersonalData: SingleLiveData<Unit> = SingleLiveData()

    init {
        getLocalAccount()
    }

    private fun getLocalAccount() {
        viewModelScope.launch {
            val localAccountDeferred = async { getLocalAccountUseCase.invoke() }
            localAccount = localAccountDeferred.await()
        }
    }

    fun saveLocalPersonalData(name: String, lastName: String) {
        viewModelScope.launch {
            when(updatePersonalDataUseCase.invoke(
                localAccount?.email,
                name,
                lastName
            )) {
                RoomOperation.SuccessOperation -> {
                    onUpdatePersonalData.safeSetValue(Unit)
                }

                RoomOperation.ErrorOperation -> {
                    onShowErrorMLD.safeSetValue(
                        "No se pudo guardar su información, inténtelo más tarde."
                    )
                }
            }
        }
    }
    fun navigateToSetPassword() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_personalDataFragment_to_passwordFragment
            )
        )
    }

    fun onUpdatePersonalData(): LiveData<Unit> = onUpdatePersonalData
}