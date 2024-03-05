package com.example.storichallenge.modules.onboarding.cameraUsageWarningScreen.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storichallenge.databinding.FragmentCameraUsageWarningBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.modules.onboarding.cameraUsageWarningScreen.presentation.viewModel.CameraUsageWarningViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraUsageWarningFragment : Fragment() {

    private var binding by autoCleared<FragmentCameraUsageWarningBinding>()
    private val viewModel by viewModels<CameraUsageWarningViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraUsageWarningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            btnContinue.debounceClick {
                viewModel.navigateToCameraView()
            }
        }
    }

}