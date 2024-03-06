package com.example.storichallenge.modules.unlock.presentation.view

import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentUnlockBinding
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.unlock.presentation.viewModel.UnlockViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnlockFragment : BaseFragment<FragmentUnlockBinding, UnlockViewModel>() {
    override val binding: FragmentUnlockBinding by viewBinding {
        FragmentUnlockBinding.inflate(layoutInflater)
    }
    override val viewModel: UnlockViewModel by viewModels()

    override fun setupView() {

    }
}