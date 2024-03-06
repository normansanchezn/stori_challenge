package com.example.storichallenge.modules.login.presentation.viewModel

import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(): BaseViewModel() {

    fun navigateToOnboarding() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_loginFragment_to_permissionsFragment
            )
        )
    }

    fun navigateToSignIn() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_loginFragment_to_signInFragment
            )
        )
    }
}