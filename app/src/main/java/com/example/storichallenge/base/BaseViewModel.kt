package com.example.storichallenge.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.constants.StoriConstants
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.utils.SingleLiveData

abstract class BaseViewModel: ViewModel() {

    protected val onNavigationActionMLD = SingleLiveData<NavigationAction>()
    protected val onShowLoadingMLD = SingleLiveData<Boolean>()
    protected val onShowErrorMLD = SingleLiveData<String>()

    open fun setup(arguments: Bundle?) {}

    protected fun showGenericErrorMessage() {
        onShowErrorMLD.safeSetValue(StoriConstants.GENERIC_ERROR_MESSAGE)
    }

    fun onNavigationEvent(): LiveData<NavigationAction> = onNavigationActionMLD
    fun onGetShowLoading(): LiveData<Boolean> = onShowLoadingMLD
    fun onGetShowError(): LiveData<String> = onShowErrorMLD

}