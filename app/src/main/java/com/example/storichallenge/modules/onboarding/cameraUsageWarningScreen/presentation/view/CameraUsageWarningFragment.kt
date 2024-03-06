package com.example.storichallenge.modules.onboarding.cameraUsageWarningScreen.presentation.view

import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentCameraUsageWarningBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.onboarding.cameraUsageWarningScreen.presentation.viewModel.CameraUsageWarningViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraUsageWarningFragment :
    BaseFragment<FragmentCameraUsageWarningBinding,CameraUsageWarningViewModel>() {

    override val binding: FragmentCameraUsageWarningBinding by viewBinding {
        FragmentCameraUsageWarningBinding.inflate(layoutInflater)
    }
    override val viewModel: CameraUsageWarningViewModel by viewModels()

    override fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    override fun initListeners() {
        with(binding) {
            btnContinue.debounceClick {
                viewModel.navigateToCameraView()
            }
        }
    }
}