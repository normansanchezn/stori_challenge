package com.example.storichallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.utils.SingleLiveData
import kotlinx.coroutines.launch

//@HiltViewModel
class MainViewModel /*@Inject constructor(

)*/: BaseViewModel() {

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