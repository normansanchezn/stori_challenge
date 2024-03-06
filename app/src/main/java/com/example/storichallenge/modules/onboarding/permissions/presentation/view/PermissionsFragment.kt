package com.example.storichallenge.modules.onboarding.permissions.presentation.view

import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.fragment.app.viewModels
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentPermissionsBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.gone
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.onboarding.permissions.data.model.PermissionsList
import com.example.storichallenge.modules.onboarding.permissions.presentation.viewModel.PermissionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionsFragment : BaseFragment<FragmentPermissionsBinding, PermissionsViewModel>() {

    override val binding: FragmentPermissionsBinding by viewBinding {
        FragmentPermissionsBinding.inflate(layoutInflater)
    }
    override val viewModel: PermissionsViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val denied = permissions.entries.filter { it.value.not() }
            binding.btnContinue.isEnabled = denied.isEmpty()
        }

    override fun initListeners() {
        with(binding) {
            btnContinue.debounceClick {
                viewModel.navigateToEmailData()
            }
        }
    }

    override fun initObservers() {
        with(viewModel) {
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestPermissionLauncher.launch(PermissionsList.permissions)
        biometricsAvailable()
    }

    private fun biometricsAvailable() {
        val biometricManager = BiometricManager.from(requireContext())

        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                hideBiometricData()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED,
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED,
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN,
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                hideBiometricData()
            }
        }
    }

    private fun hideBiometricData() {
        with(binding) {
            iconLock.gone()
            biometricAuth.gone()
            biometricAuthDescription.gone()
        }
    }
}