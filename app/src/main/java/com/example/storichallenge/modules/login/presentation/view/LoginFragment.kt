package com.example.storichallenge.modules.login.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storichallenge.databinding.FragmentLoginBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.modules.login.presentation.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding by autoCleared<FragmentLoginBinding>()
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    private fun setupView() {
        with(binding) {
            createAccount.debounceClick {
                viewModel.navigateToOnboarding()
            }

            login.debounceClick {
                viewModel.navigateToSignIn()
            }
        }
    }
}