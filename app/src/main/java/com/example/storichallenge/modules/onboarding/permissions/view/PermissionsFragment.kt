package com.example.storichallenge.modules.onboarding.permissions.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storichallenge.databinding.FragmentPermissionsBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.gone
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.modules.onboarding.permissions.data.model.PermissionsList
import com.example.storichallenge.modules.onboarding.permissions.viewModel.PermissionsViewModel

class PermissionsFragment : Fragment() {

    private var binding by autoCleared<FragmentPermissionsBinding>()
    private val viewModel by viewModels<PermissionsViewModel>()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val denied = permissions.entries.filter { it.value.not() }
            binding.btnContinue.isEnabled = denied.isEmpty()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            btnContinue.debounceClick {
                viewModel.navigateToPersonalData()
            }
        }
    }

    private fun initObservers() {
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