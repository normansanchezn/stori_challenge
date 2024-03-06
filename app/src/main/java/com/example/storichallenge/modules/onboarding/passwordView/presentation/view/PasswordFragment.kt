package com.example.storichallenge.modules.onboarding.passwordView.presentation.view

import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentPasswordBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.onboarding.passwordView.presentation.viewModel.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding, PasswordViewModel>() {

    override val binding: FragmentPasswordBinding by viewBinding {
        FragmentPasswordBinding.inflate(layoutInflater)
    }
    override val viewModel: PasswordViewModel by viewModels()
    private var passwordValidated = false

    override fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    override fun initListeners() {
        with(binding.passwordLayout) {
            password.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

                override fun afterTextChanged(s: Editable?) {
                    validatePassword(s.toString())
                }
            })

            rePassword.doAfterTextChanged {
                binding.btnContinue.isEnabled = passwordValidated && validatePasswords()
            }
        }

        binding.btnContinue.debounceClick {
            viewModel.navigateToCameraUsageWarning()
        }
    }

    private fun validatePasswords(): Boolean {
        return binding.passwordLayout.password.text.toString() == binding.passwordLayout.rePassword.text.toString()
    }

    private fun validatePassword(password: String) {
        val containsUppercase = Regex("[A-Z]").containsMatchIn(password)
        val containsSpecialCharacter = Regex("[^A-Za-z0-9]").containsMatchIn(password)
        val containsNumber = Regex("[0-9]").containsMatchIn(password)
        val hasValidLength = password.length >= 8

        with(binding.passwordLayout) {
            cbCapitalLetters.isChecked = containsUppercase
            cbSpecialCharacters.isChecked = containsSpecialCharacter
            cbEightCharacters.isChecked = hasValidLength
            cbAtLeastOneNumber.isChecked = containsNumber

            passwordValidated = containsUppercase && containsSpecialCharacter && containsNumber && hasValidLength
        }
    }
}