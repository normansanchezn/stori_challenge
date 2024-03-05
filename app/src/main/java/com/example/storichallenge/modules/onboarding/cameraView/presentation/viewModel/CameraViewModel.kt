package com.example.storichallenge.modules.onboarding.cameraView.presentation.viewModel

import android.net.Uri
import android.os.Bundle
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.onboarding.utils.OnboardingConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(): BaseViewModel() {

    fun navigateToPreviewPhoto(imageUri: Uri?) {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_cameraViewFragment_to_previewImageFragment,
                Bundle().apply {
                    putParcelable(OnboardingConstants.BUNDLE_URI_PHOTO, imageUri)
                }
            )
        )
    }
}