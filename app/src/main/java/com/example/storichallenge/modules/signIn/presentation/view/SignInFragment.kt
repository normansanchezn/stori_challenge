package com.example.storichallenge.modules.signIn.presentation.view

import android.content.Intent
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.base.model.DialogTexts
import com.example.storichallenge.databinding.FragmentSignInBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.setOptionsMenu
import com.example.storichallenge.extensions.setUpFragmentToolBar
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.home.HomeActivity
import com.example.storichallenge.modules.signIn.presentation.viewModel.SignInViewModel
import com.example.storichallenge.utils.StoriPatterns
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {
    override val binding: FragmentSignInBinding by viewBinding {
        FragmentSignInBinding.inflate(layoutInflater)
    }
    override val viewModel: SignInViewModel by viewModels()
    private var isCorrectEmail: Boolean? = null
    private var isCorrectPassword: Boolean? = null

    override fun initListeners() {
        with(binding) {
            email.doOnTextChanged { text, _, _, _ ->
                emailLayout.error = ""
                val regex = Regex(StoriPatterns.EMAIL_PATTERN)
                val matched = regex.matches(text?.trim().toString())
                isCorrectEmail = matched
                if (matched.not()) {
                    emailLayout.error = getString(R.string.txt_enter_valid_email)
                }
                enableButton()
            }
            password.doOnTextChanged { text, _, _, _ ->
                isCorrectPassword = text.toString().isNotEmpty()
                enableButton()
            }

            loginButton.debounceClick {
                viewModel.loginWithCredentials(email.text.toString(), password.text.toString())
            }
        }
    }

    override fun initObservers() {
        with(viewModel) {
            onGetShowError().observe(viewLifecycleOwner) { error ->
                showMessageDialog(
                    DialogTexts(
                        message = error,
                        primaryButtonText = getString(R.string.txt_try_later)
                    )
                )
            }
            onLogin().observe(viewLifecycleOwner) {
                requireActivity().finish()
                startActivity(Intent(requireContext(), HomeActivity::class.java))
            }
        }
    }

    private fun enableButton() {
        binding.loginButton.isEnabled = isCorrectEmail == true && isCorrectPassword == true
    }

    override fun setupView() {
        setUpFragmentToolBar(
            binding.toolbar.toolbarSingleTitle,
        )
        setOptionsMenu()
    }

}