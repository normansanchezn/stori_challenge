package com.example.storichallenge.modules.loaderDialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.storichallenge.R
import com.example.storichallenge.databinding.FragmentLoginBinding
import com.example.storichallenge.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class LoaderDialogFragment : DialogFragment() {

    private val binding by viewBinding {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun getTheme(): Int = R.style.DialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    fun setupView() {
        isCancelable = false
        // binding.imageViewLogoRuut.startAnimation(animation)
    }

}