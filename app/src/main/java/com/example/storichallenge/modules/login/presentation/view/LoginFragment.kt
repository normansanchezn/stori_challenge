package com.example.storichallenge.modules.login.presentation.view

import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentLoginBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.login.presentation.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val binding: FragmentLoginBinding by viewBinding {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    override val viewModel: LoginViewModel by viewModels()

    override fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    override fun setupView() {
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