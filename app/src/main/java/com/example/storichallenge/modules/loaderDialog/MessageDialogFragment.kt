package com.example.storichallenge.modules.loaderDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.storichallenge.R
import com.example.storichallenge.base.model.DialogTexts
import com.example.storichallenge.databinding.FragmentMessageDialogBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MessageDialogFragment @Inject constructor() : DialogFragment() {

    private var binding by autoCleared<FragmentMessageDialogBinding>()

    private var onPositiveAction: (() -> Unit)? = null
    private var onNegativeAction: (() -> Unit)? = null
    private var dialogTexts: DialogTexts? = null

    override fun getTheme(): Int = R.style.DialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageDialogBinding.inflate(layoutInflater)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

        dialogTexts?.let { texts ->
            with(binding) {
                if (texts.title.isNullOrEmpty().not()) textDialogTitle.visible()
                buttonSecondary.isVisible = !texts.secondaryButtonText.isNullOrEmpty()
                textDialogTitle.text = texts.title
                textDialogMessage.text = texts.message
                buttonDialogMessage.text = texts.primaryButtonText
                buttonSecondary.text = texts.secondaryButtonText
            }
        }

        binding.buttonDialogMessage.setOnClickListener {
            dismiss()
            onPositiveAction?.invoke()
        }

        binding.buttonSecondary.setOnClickListener {
            dismiss()
            onNegativeAction?.invoke()
        }

    }

    fun setupView(dialogTexts: DialogTexts, onCallbackClick: (() -> Unit)?, onNegativeClick: (() -> Unit)?) {
        this.dialogTexts = dialogTexts
        initListeners(onCallbackClick, onNegativeClick)
    }

    private fun initListeners(onCallbackClick: (() -> Unit)?, onNegativeClick: (() -> Unit)?) {
        onPositiveAction = onCallbackClick
        onNegativeAction = onNegativeClick
    }

}