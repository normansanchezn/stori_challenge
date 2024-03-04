package com.example.storichallenge.modules.onboarding.personalData.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storichallenge.databinding.FragmentPersonalDataBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.modules.onboarding.personalData.viewModel.PersonalDataViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PersonalDataFragment : Fragment() {

    private var binding by autoCleared<FragmentPersonalDataBinding>()
    private val viewModel by viewModels<PersonalDataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

}