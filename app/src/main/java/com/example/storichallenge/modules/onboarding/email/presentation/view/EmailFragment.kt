package com.example.storichallenge.modules.onboarding.email.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.storichallenge.R
import com.example.storichallenge.databinding.FragmentEmailBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.modules.onboarding.email.presentation.viewModel.EmailViewModel
import com.example.storichallenge.utils.StoriPatterns
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment : Fragment() {

    private var binding by autoCleared<FragmentEmailBinding>()
    private val viewModel by viewModels<EmailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    private fun initListeners() {
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
                viewModel.navigateToPersonalInformation()
            }
        }
    }
}