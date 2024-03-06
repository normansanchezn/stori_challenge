package com.example.storichallenge.modules.onboarding.preview.presentation.view

import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentPreviewImageBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.onboarding.preview.presentation.viewModel.PreviewImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewImageFragment : BaseFragment<FragmentPreviewImageBinding, PreviewImageViewModel>() {

    override val binding: FragmentPreviewImageBinding by viewBinding {
        FragmentPreviewImageBinding.inflate(layoutInflater)
    }
    override val viewModel: PreviewImageViewModel by viewModels()

    override fun initObservers() {
        with(viewModel) {
            onGetImageUri().observe(viewLifecycleOwner) { imageUri ->
                binding.resultPhoto.setImageURI(imageUri)
            }

            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    override fun initListeners() {
        with(binding) {
            btnContinue.debounceClick {
                viewModel.navigateToSuccessView()
            }
        }
    }
}