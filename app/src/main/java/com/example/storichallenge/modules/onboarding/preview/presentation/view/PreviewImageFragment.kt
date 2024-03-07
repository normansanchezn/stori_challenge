package com.example.storichallenge.modules.onboarding.preview.presentation.view

import android.net.Uri
import android.util.Base64
import androidx.fragment.app.viewModels
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.base.model.DialogTexts
import com.example.storichallenge.databinding.FragmentPreviewImageBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.onboarding.preview.presentation.viewModel.PreviewImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class PreviewImageFragment : BaseFragment<FragmentPreviewImageBinding, PreviewImageViewModel>() {

    override val binding: FragmentPreviewImageBinding by viewBinding {
        FragmentPreviewImageBinding.inflate(layoutInflater)
    }
    override val viewModel: PreviewImageViewModel by viewModels()

    override fun setupView() {
        viewModel.setup(arguments)
    }

    override fun initObservers() {
        with(viewModel) {
            onGetImageUri().observe(viewLifecycleOwner) { imageUri ->
                binding.resultPhoto.setImageURI(imageUri)
                viewModel.holdImage(convertToBase64(imageUri))
            }

            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }

            onGetShowError().observe(viewLifecycleOwner) { error ->
                showMessageDialog(
                    DialogTexts(
                        message = error,
                        primaryButtonText = getString(R.string.txt_try_later)
                    )
                )
            }

            onUpdateLocalIdPhoto().observe(viewLifecycleOwner) {
                viewModel.createRemoteUser()
            }
        }
    }

    private fun convertToBase64(imageUri: Uri): String {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(imageUri)
            val buffer = ByteArray(inputStream?.available() ?: 0)
            inputStream?.read(buffer)
            Base64.encodeToString(buffer, Base64.DEFAULT)
        } catch (e: IOException) {
            e.message.toString()
        }
    }

    override fun initListeners() {
        with(binding) {
            btnContinue.debounceClick {
                viewModel.updateLocalIdPhoto()
            }
        }
    }
}