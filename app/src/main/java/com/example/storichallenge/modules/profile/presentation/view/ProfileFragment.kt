package com.example.storichallenge.modules.profile.presentation.view

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.storichallenge.MainActivity
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentProfileBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.setOptionsMenu
import com.example.storichallenge.extensions.setUpFragmentToolBar
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.profile.presentation.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val binding: FragmentProfileBinding by viewBinding {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    override val viewModel: ProfileViewModel by viewModels()

    override fun setupView() {
        setUpFragmentToolBar(
            binding.toolbar.toolbarSingleTitle,
            title = getString(R.string.txt_profile)
        )
        setOptionsMenu()
        viewModel.getAccount()
    }

    override fun initListeners() {
        with(binding) {
            btnSignOut.debounceClick {
                viewModel.signOut()
            }
        }
    }

    override fun initObservers() {
        with(viewModel) {
            onGetAccount().observe(viewLifecycleOwner) { account ->
                setupAccountView(account)
            }
            onSignOut().observe(viewLifecycleOwner) {
                requireActivity().finish()
                startActivity(
                    Intent(requireContext(), MainActivity::class.java)
                )
            }
        }
    }

    private fun setupAccountView(account: Account) {
        with(binding.profileLayout) {
            name.text = account.name
            lastName.text = account.lastName
        }
    }

}