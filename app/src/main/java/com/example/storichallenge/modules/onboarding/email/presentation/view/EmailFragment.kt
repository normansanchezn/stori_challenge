package com.example.storichallenge.modules.onboarding.email.presentation.view

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.base.model.DialogTexts
import com.example.storichallenge.databinding.FragmentEmailBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.onboarding.email.presentation.viewModel.EmailViewModel
import com.example.storichallenge.utils.StoriPatterns
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment : BaseFragment<FragmentEmailBinding, EmailViewModel>() {

    override val binding: FragmentEmailBinding by viewBinding {
        FragmentEmailBinding.inflate(layoutInflater)
    }
    override val viewModel: EmailViewModel by viewModels()

    override fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
            onGetShowError().observe(viewLifecycleOwner) { error ->
                showMessageDialog(
                    DialogTexts(
                        message = error,
                        primaryButtonText = getString(R.string.txt_try_later)
                    )
                )
            }
            onCreateAccount().observe(viewLifecycleOwner) {
                viewModel.navigateToPersonalInformation()
            }
        }
    }

    override fun initListeners() {
        with(binding) {
            email.doOnTextChanged { text, _, _, _ ->
                emailLayout.error = ""
                val regex = Regex(StoriPatterns.EMAIL_PATTERN)
                val matched = regex.matches(text?.trim().toString())
                saveEmailButton.isEnabled = matched
                if (matched.not()) {
                    emailLayout.error = getString(R.string.txt_enter_valid_email)
                }
            }

            saveEmailButton.debounceClick {
                viewModel.saveLocalEmailAddress(email.text.toString())
            }
        }
    }
}