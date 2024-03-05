package com.example.storichallenge.modules.onboarding.cameraUsageWarningScreen.presentation.viewModel

import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraUsageWarningViewModel @Inject constructor() : BaseViewModel() {

    fun navigateToCameraView() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_cameraUsageWarningFragment_to_cameraViewFragment
            )
        )
    }

}