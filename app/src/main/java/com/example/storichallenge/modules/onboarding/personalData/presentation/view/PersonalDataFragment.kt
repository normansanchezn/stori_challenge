package com.example.storichallenge.modules.onboarding.personalData.presentation.view

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentPersonalDataBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.onboarding.personalData.presentation.viewModel.PersonalDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalDataFragment : BaseFragment<FragmentPersonalDataBinding, PersonalDataViewModel>() {

    override val binding: FragmentPersonalDataBinding by viewBinding {
        FragmentPersonalDataBinding.inflate(layoutInflater)
    }
    override val viewModel: PersonalDataViewModel by viewModels()

    override fun initListeners() {
        with(binding.layoutPersonalData) {
            name.doAfterTextChanged { enableContinueButton() }
            lastName.doAfterTextChanged { enableContinueButton() }
        }

        binding.continueButton.debounceClick {
            viewModel.navigateToSetPassword()
        }
    }

    private fun enableContinueButton() {
        binding.continueButton.isEnabled = binding.layoutPersonalData.name.text?.toString()?.isNotEmpty()!! &&
                binding.layoutPersonalData.lastName.text.toString().isNotEmpty()
    }

    override fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }
}