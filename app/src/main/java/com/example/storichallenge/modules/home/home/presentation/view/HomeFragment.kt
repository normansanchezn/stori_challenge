package com.example.storichallenge.modules.home.home.presentation.view

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storichallenge.R
import com.example.storichallenge.base.BaseFragment
import com.example.storichallenge.databinding.FragmentHomeBinding
import com.example.storichallenge.extensions.setUpFragmentHomeToolBar
import com.example.storichallenge.extensions.viewBinding
import com.example.storichallenge.modules.home.data.mock.listMock
import com.example.storichallenge.modules.home.home.presentation.view.adapter.TransactionHistoryAdapter
import com.example.storichallenge.modules.home.home.presentation.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val binding: FragmentHomeBinding by viewBinding {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    override val viewModel: HomeViewModel by viewModels()
    private val transactionHistoryAdapter by lazy { TransactionHistoryAdapter() }


    override fun setupView() {
        setUpFragmentHomeToolBar(
            binding.toolbar.toolbarSingleTitle,
            getString(R.string.txt_home)
        )
        configTransactionHistoryRecycler()
        binding.amount.text = getString(R.string.txt_dummy_amount)
    }

    private fun configTransactionHistoryRecycler() {
        with(binding.rvTransactionHistory) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionHistoryAdapter
        }
        transactionHistoryAdapter.setData(listMock())
    }

    override fun initListeners() {

    }

}