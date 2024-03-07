package com.example.storichallenge.modules.unlock.presentation.view

import android.content.Intent
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.base.model.DialogTexts
import com.example.storichallenge.databinding.FragmentUnlockBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.gone
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.extensions.visible
import com.example.storichallenge.modules.home.HomeActivity
import com.example.storichallenge.modules.unlock.presentation.viewModel.UnlockViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

@AndroidEntryPoint
class UnlockFragment : BaseFragment<FragmentUnlockBinding, UnlockViewModel>() {
    override val binding: FragmentUnlockBinding by viewBinding {
        FragmentUnlockBinding.inflate(layoutInflater)
    }
    override val viewModel: UnlockViewModel by viewModels()

    private lateinit var executor: Executor
    private lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo
    private lateinit var biometricPrompt: BiometricPrompt

    override fun setupView() {
        checkIfBiometricIsAvailable()
    }

    override fun initObservers() {
        with(viewModel) {
            onGetShowError().observe(viewLifecycleOwner) { error ->
                showMessageDialog(
                    DialogTexts(
                        message = error,
                        primaryButtonText = getString(R.string.txt_try_later)
                    )
                )
            }

            onLogin().observe(viewLifecycleOwner) {
                requireActivity().finish()
                startActivity(
                    Intent(requireContext(), HomeActivity::class.java)
                )
            }

            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    override fun initListeners() {
        with(binding) {
            unlockPassword.debounceClick {
                viewModel.navigateToLoginView()
            }

            unlockBiometric.debounceClick {
                biometricAuthentication()
            }
        }
    }

    private fun biometricAuthentication() {
        executor = ContextCompat.getMainExecutor(requireContext().applicationContext)

        biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.txt_enter_stori))
            .setSubtitle(getString(R.string.txt_scan_fingerprint))
            .setNegativeButtonText(getString(R.string.txt_use_password))
            .build()

        biometricPrompt = BiometricPrompt(this@UnlockFragment, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON)
                    viewModel.navigateToLoginView()
                else if (errorCode == BiometricPrompt.ERROR_NO_BIOMETRICS || errorCode == BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL)
                    Toast.makeText(requireContext(), errString, Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                viewModel.loginWithUser()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                viewModel.navigateToLoginView()
            }
        })

        biometricPrompt.authenticate(biometricPromptInfo)
    }

    private fun checkIfBiometricIsAvailable() {
        val biometricManager = BiometricManager.from(requireContext())
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                binding.unlockBiometric.visible()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED,
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED,
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN,
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                binding.unlockBiometric.gone()
            }
        }
    }
}