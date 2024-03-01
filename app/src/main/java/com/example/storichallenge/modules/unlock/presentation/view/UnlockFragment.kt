package com.example.storichallenge.modules.unlock.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentUnlockBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.unlock.presentation.viewModel.UnlockViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnlockFragment : Fragment() {

    private var binding by autoCleared<FragmentUnlockBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUnlockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {

    }

}