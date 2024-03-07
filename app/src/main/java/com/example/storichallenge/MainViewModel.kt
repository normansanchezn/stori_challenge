package com.example.storichallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.domain.useCases.GetLocalAccountUseCase
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocalAccountUseCase: GetLocalAccountUseCase
): BaseViewModel() {

    private val onNavigateTo = SingleLiveData<StartNavigation>()
    private var localAccount: Account ?= null

    fun setNavigationDest() {
        viewModelScope.launch {
            val localAccountDeferred = async { getLocalAccountUseCase.invoke() }
            localAccount = localAccountDeferred.await()
            onNavigateTo.safeSetValue(
                if (localAccount == null) {
                    StartNavigation.NavigateLogin
                } else {
                    StartNavigation.NavigateUnlock
                }
            )
        }
    }

    fun onNavigateTo(): LiveData<StartNavigation> = onNavigateTo

    sealed class StartNavigation {
        object NavigateUnlock : StartNavigation()
        object NavigateLogin : StartNavigation()
    }
}