package com.example.storichallenge.modules.signIn.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storichallenge.databinding.FragmentSignInBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.setOptionsMenu
import com.example.storichallenge.extensions.setUpFragmentToolBar

class SignInFragment : Fragment() {

    private var binding by autoCleared<FragmentSignInBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initListeners()
    }

    private fun initListeners() {
        setUpFragmentToolBar(
            binding.toolbar.toolbarSingleTitle,
        )
        setOptionsMenu()
    }

    private fun setupView() {

    }

}