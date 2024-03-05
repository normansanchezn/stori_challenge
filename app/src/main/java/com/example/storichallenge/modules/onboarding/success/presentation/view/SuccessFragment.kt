package com.example.storichallenge.modules.onboarding.success.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.storichallenge.R
import com.example.storichallenge.databinding.FragmentSuccessBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.modules.onboarding.success.presentation.viewModel.SuccessViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuccessFragment : Fragment() {

    private var binding by autoCleared<FragmentSuccessBinding>()
    private val viewModel by viewModels<SuccessViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
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