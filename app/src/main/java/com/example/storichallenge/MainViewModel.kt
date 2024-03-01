package com.example.storichallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel: BaseViewModel() {

    private val onNavigateTo = SingleLiveData<StartNavigation>()

    fun setNavigationDest() {
        viewModelScope.launch {
            // validate if has account
            onNavigateTo.safeSetValue(StartNavigation.NavigateLogin)
        }
    }

    fun onNavigateTo(): LiveData<StartNavigation> = onNavigateTo

    sealed class StartNavigation {
        object NavigateUnlock : StartNavigation()
        object NavigateLogin : StartNavigation()
    }
}