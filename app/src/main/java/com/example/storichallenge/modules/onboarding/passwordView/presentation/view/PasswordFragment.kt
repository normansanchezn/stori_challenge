package com.example.storichallenge.modules.onboarding.passwordView.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storichallenge.databinding.FragmentPasswordBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.modules.onboarding.passwordView.presentation.viewModel.PasswordViewModel

class PasswordFragment : Fragment() {

    private var binding by autoCleared<FragmentPasswordBinding>()
    private val viewModel by viewModels<PasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}