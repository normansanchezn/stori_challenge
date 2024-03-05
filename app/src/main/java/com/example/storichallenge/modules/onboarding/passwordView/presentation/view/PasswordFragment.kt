package com.example.storichallenge.modules.onboarding.passwordView.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storichallenge.databinding.FragmentPasswordBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.modules.onboarding.passwordView.presentation.viewModel.PasswordViewModel

class PasswordFragment : Fragment() {

    private var binding by autoCleared<FragmentPasswordBinding>()
    private val viewModel by viewModels<PasswordViewModel>()

    private var passwordValidated = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
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