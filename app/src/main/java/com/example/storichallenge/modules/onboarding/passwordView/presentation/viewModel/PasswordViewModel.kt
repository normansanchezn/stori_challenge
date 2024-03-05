package com.example.storichallenge.modules.onboarding.passwordView.presentation.viewModel

import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(): BaseViewModel() {

    fun navigateToCameraUsageWarning() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_passwordFragment_to_cameraUsageWarningFragment
            )
        )
    }
}