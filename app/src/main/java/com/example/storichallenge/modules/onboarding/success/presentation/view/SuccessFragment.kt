package com.example.storichallenge.modules.onboarding.success.presentation.view

import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentSuccessBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.onboarding.success.presentation.viewModel.SuccessViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : BaseFragment<FragmentSuccessBinding, SuccessViewModel>() {
    override val binding: FragmentSuccessBinding by viewBinding {
        FragmentSuccessBinding.inflate(layoutInflater)
    }
    override val viewModel: SuccessViewModel by viewModels()

    override fun initListeners() {
        with(binding) {
            animationView.playAnimation()

            btnFinish.debounceClick {
                requireActivity().finish()
                // Start new activity
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.animationView.cancelAnimation()
    }

}