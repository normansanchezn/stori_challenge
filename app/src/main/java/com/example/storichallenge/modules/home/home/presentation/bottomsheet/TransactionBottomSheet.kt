package com.example.storichallenge.modules.home.home.presentation.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.example.storichallenge.R
import com.example.storichallenge.databinding.BottomSheetTransactionHistoryBinding
import com.example.storichallenge.extensions.toMoneyFormat
import com.example.storichallenge.modules.home.data.model.TransactionItem
import com.example.storichallenge.modules.home.data.model.TypeConcept
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TransactionBottomSheet(
    private val transactionItem: TransactionItem
):  BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTransactionHistoryBinding

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetTransactionHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        with(binding) {
            title.text = transactionItem.concept
            date.text = transactionItem.timestamp
            amount.text = transactionItem.amount?.toMoneyFormat()

            when(transactionItem.concept) {
                TypeConcept.TRANSFER.typeText -> {
                    amount.setTextColor(requireContext().getColor(R.color.stori_primary))
                    icon.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.icon_transfer))
                }
                TypeConcept.WITHDRAW.typeText -> {
                    amount.setTextColor(requireContext().getColor(R.color.red))
                    icon.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.icon_withdraw))
                }
            }
        }
    }
}