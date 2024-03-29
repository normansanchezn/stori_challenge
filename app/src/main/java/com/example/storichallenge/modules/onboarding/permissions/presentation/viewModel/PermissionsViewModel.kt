package com.example.storichallenge.modules.onboarding.permissions.presentation.viewModel

import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor(): BaseViewModel() {

    fun navigateToEmailData() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_permissionsFragment_to_emailFragment
            )
        )
    }
}