package com.example.storichallenge.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.storichallenge.base.model.DialogTexts
import com.example.storichallenge.constants.StoriConstants
import com.example.storichallenge.modules.loaderDialog.LoaderDialogFragment
import com.example.storichallenge.modules.loaderDialog.MessageDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
) : Fragment() {

    /**
     * This ViewBinding will allow us to create our View
     *
     * Example of implementation:
     * override val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::inflate)
     */
    abstract val binding: VB

    /**
     * This ViewModel will allow us to keep the logic separate from our UI.
     *
     * Example of implementation:
     * override val viewModel: MainViewModel by viewModels()
     */
    abstract val viewModel: VM

    @Inject
    lateinit var loaderDialogFragment: LoaderDialogFragment

    @Inject
    lateinit var messageDialogFragment: MessageDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initListeners()
        initObservers()
        /*FirebaseCrashlytics.getInstance().log(
            this.javaClass.simpleName
        )*/
    }


    /**
     * Override it to be able to configure the view together with the associated ViewBinding.
     * Example:
     *  override fun initView() = with(binding) {
     *      someTextView.text = "Binding!!"
     *  }
     */
    protected open fun setupView() = Unit

    protected open fun initListeners() = Unit

    protected open fun initObservers() = Unit

    fun shouldShowLoadingDialog(shouldShowProgress: Boolean) {
        if (shouldShowProgress) {
            // It added isVisible because it already is visible does not need to show again the dialog and avoid crash.
            if (!loaderDialogFragment.isVisible && !loaderDialogFragment.isAdded) {
                /*
                This line add to avoid if state of fragment is destroyed and state loss and generate
                IllegalStateException that means FragmentTransaction and commit is call alter onSaveInstanceState
             */
                if (!childFragmentManager.isDestroyed && !childFragmentManager.isStateSaved) {
                    loaderDialogFragment.show(
                        childFragmentManager,
                        StoriConstants.DIALOG_TAG
                    )
                }
            }
        } else {
            if (loaderDialogFragment.isVisible) {
                loaderDialogFragment.dismiss()
            }
        }
    }

    fun showMessageDialog(
        dialogTexts: DialogTexts,
        onPositiveAction: (() -> Unit)? = null,
        onNegativeAction: (() -> Unit)? = null
    ) {
        if (!messageDialogFragment.isVisible && !messageDialogFragment.isAdded) {
            if (!childFragmentManager.isDestroyed && !childFragmentManager.isStateSaved) {
                messageDialogFragment.setupView(dialogTexts, onPositiveAction, onNegativeAction)
                messageDialogFragment.show(
                    childFragmentManager,
                    StoriConstants.DIALOG_TAG
                )
            }
        }
    }
}