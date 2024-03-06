package com.example.storichallenge.modules.loaderDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.storichallenge.R
import com.example.storichallenge.databinding.FragmentDialogLoaderBinding
import com.example.storichallenge.extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoaderDialogFragment @Inject constructor(): DialogFragment() {

    private var binding by autoCleared<FragmentDialogLoaderBinding>()

    override fun getTheme(): Int = R.style.DialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogLoaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        isCancelable = false
    }
}