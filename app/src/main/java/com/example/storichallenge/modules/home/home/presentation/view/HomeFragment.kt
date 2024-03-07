package com.example.storichallenge.modules.home.home.presentation.view

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.constants.StoriConstants.TRANSACTION_ITEM_BOTTOM_SHEET
import com.example.storichallenge.databinding.FragmentHomeBinding
import com.example.storichallenge.extensions.gone
import com.example.storichallenge.extensions.navigateTo
import com.example.storichallenge.extensions.setOptionsMenu
import com.example.storichallenge.extensions.setUpFragmentHomeToolBar
import com.example.storichallenge.extensions.toMoneyFormat
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.extensions.visible
import com.example.storichallenge.modules.home.data.model.TransactionItem
import com.example.storichallenge.modules.home.home.presentation.bottomsheet.TransactionBottomSheet
import com.example.storichallenge.modules.home.home.presentation.view.adapter.TransactionHistoryAdapter
import com.example.storichallenge.modules.home.home.presentation.viewModel.HomeViewModel
import com.example.storichallenge.modules.home.home.utils.TransactionObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val binding: FragmentHomeBinding by viewBinding {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    override val viewModel: HomeViewModel by viewModels()
    private val transactionHistoryAdapter by lazy { TransactionHistoryAdapter(onItemClick = onItemClick) }
    private var totalBalanceLocal: Double ?= null

    override fun setupView() {
        setUpFragmentHomeToolBar(
            binding.toolbar.toolbarSingleTitle,
            getString(R.string.txt_home)
        )
        setOptionsMenu(menuHomeCallback = {
            viewModel.navigateToProfile()
        })
        configTransactionHistoryRecycler()
        viewModel.getTotalBalance()
    }

    private fun configTransactionHistoryRecycler() {
        with(binding.rvTransactionHistory) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionHistoryAdapter
        }
    }


    override fun initObservers() {
        with(viewModel) {
            onGetTotalBalance().observe(viewLifecycleOwner) { accountBalance ->
                viewModel.getTransactionHistory()
                binding.amount.text = if (accountBalance.balance == null || accountBalance.balance == "null") {
                    totalBalanceLocal = 0.0
                    0.0.toMoneyFormat()
                } else {
                    totalBalanceLocal = accountBalance.balance.toDouble()
                    accountBalance.balance.toDouble().toMoneyFormat()
                }
            }

            onGetTransactionHistory().observe(viewLifecycleOwner) { transactionHistory ->
                // TODO: Change this, because is for the example!
                if (totalBalanceLocal == 0.0) {
                    binding.rvTransactionHistory.gone()
                    binding.placeholder.visible()
                    binding.animationView.visible()
                } else {
                    transactionHistoryAdapter.setData(transactionHistory)
                    binding.rvTransactionHistory.visible()
                    binding.placeholder.gone()
                    binding.animationView.gone()
                }
            }
            onNavigationEvent().observe(viewLifecycleOwner) { navEvent ->
                navigateTo(navEvent)
            }
        }
    }

    private val onItemClick: TransactionObject = { transactionItem ->
        showTransactionBottomSheetDialog(transactionItem)
    }

    private fun showTransactionBottomSheetDialog(transactionItem: TransactionItem) {
        TransactionBottomSheet(transactionItem).show(
            childFragmentManager,
            TRANSACTION_ITEM_BOTTOM_SHEET
        )
    }
}