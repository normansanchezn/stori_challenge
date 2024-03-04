package com.example.storichallenge.modules.onboarding.personalData.presentation.viewModel

import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(): BaseViewModel() {

    fun navigateToSetPassword() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_personalDataFragment_to_passwordFragment
            )
        )
    }
}