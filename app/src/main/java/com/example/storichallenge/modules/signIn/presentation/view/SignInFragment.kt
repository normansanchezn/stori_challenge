package com.example.storichallenge.modules.signIn.presentation.view

import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentSignInBinding
import com.example.storichallenge.extensions.setOptionsMenu
import com.example.storichallenge.extensions.setUpFragmentToolBar
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.signIn.presentation.viewModel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {
    override val binding: FragmentSignInBinding by viewBinding {
        FragmentSignInBinding.inflate(layoutInflater)
    }
    override val viewModel: SignInViewModel by viewModels()

    override fun initListeners() {
        setUpFragmentToolBar(
            binding.toolbar.toolbarSingleTitle,
        )
        setOptionsMenu()
    }

    override fun setupView() {

    }
}