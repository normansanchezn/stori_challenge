package com.example.storichallenge.modules.onboarding.preview.presentation.viewModel

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.LiveData
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.base.model.NavigationAction
import com.example.storichallenge.extensions.parcelable
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.onboarding.utils.OnboardingConstants
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewImageViewModel @Inject constructor(): BaseViewModel() {

    private val imageUri: SingleLiveData<Uri> = SingleLiveData()

    override fun setup(arguments: Bundle?) {
        arguments?.let {
            imageUri.safeSetValue(arguments.parcelable(OnboardingConstants.BUNDLE_URI_PHOTO))
        }
    }

    fun navigateToSuccessView() {
        onNavigationActionMLD.safeSetValue(
            NavigationAction(
                R.id.action_previewImageFragment_to_successFragment
            )
        )
    }

    fun onGetImageUri(): LiveData<Uri> = imageUri

}