package com.example.storichallenge.modules.onboarding.preview.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storichallenge.databinding.FragmentPreviewImageBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.modules.onboarding.preview.presentation.viewModel.PreviewImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewImageFragment : Fragment() {

    private var binding by autoCleared<FragmentPreviewImageBinding>()
    private val viewModel by viewModels<PreviewImageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setup(arguments)
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            onGetImageUri().observe(viewLifecycleOwner) { imageUri ->
                binding.resultPhoto.setImageURI(imageUri)
            }

            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            btnContinue.debounceClick {
                viewModel.navigateToSuccessView()
            }
        }
    }
}