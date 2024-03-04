package com.example.storichallenge.modules.onboarding.email.presentation.viewModel

import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(): BaseViewModel() {

    fun navigateToPersonalInformation() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_emailFragment_to_personalDataFragment
            )
        )
    }
}