package com.example.storichallenge.modules.login.view

import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentLoginBinding
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.login.viewModel.LoginViewModel

// @AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val binding: FragmentLoginBinding by viewBinding {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    override val viewModel: LoginViewModel by viewModels()
}