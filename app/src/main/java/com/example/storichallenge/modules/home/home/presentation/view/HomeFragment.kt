package com.example.storichallenge.modules.home.home.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storichallenge.R
import com.example.storichallenge.databinding.FragmentHomeBinding
import com.example.storichallenge.extensions.autoCleared
import com.example.storichallenge.extensions.setUpFragmentHomeToolBar
import com.example.storichallenge.modules.home.data.mock.listMock
import com.example.storichallenge.modules.home.home.presentation.view.adapter.TransactionHistoryAdapter
import com.example.storichallenge.modules.home.home.presentation.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding by autoCleared<FragmentHomeBinding>()
    private val viewModel by viewModels<HomeViewModel>()
    private val transactionHistoryAdapter by lazy { TransactionHistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initListeners()
    }

    private fun setupView() {
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

    private fun initListeners() {

    }

}