package com.example.storichallenge.modules.home.home.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.storichallenge.R
import com.example.storichallenge.databinding.ItemTransactionHistoryBinding
import com.example.storichallenge.extensions.debounceClick
import com.example.storichallenge.extensions.toMoneyFormat
import com.example.storichallenge.modules.home.data.model.TransactionItem
import com.example.storichallenge.modules.home.data.model.TypeConcept
import com.example.storichallenge.modules.home.home.utils.TransactionObject

class TransactionHistoryAdapter(
    private val onItemClick: TransactionObject
) : RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder>() {

    private var listItems = listOf<TransactionItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionHistoryViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return TransactionHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        holder.bind(transactionItem = listItems[position])
    }

    fun setData(itemList: List<TransactionItem>) {
        listItems = itemList
        notifyDataSetChanged()
    }

    inner class TransactionHistoryViewHolder(private val binding: ItemTransactionHistoryBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(transactionItem: TransactionItem) {
                with(binding) {
                    root.debounceClick {
                        onItemClick.invoke(transactionItem)
                    }
                    amount.text = transactionItem.amount?.toMoneyFormat()
                    dateOfTransaction.text = transactionItem.timestamp
                    concept.text = transactionItem.concept

                    when(concept.text) {
                        TypeConcept.TRANSFER.typeText -> {
                            amount.setTextColor(binding.amount.context.getColor(R.color.stori_primary))
                            iconTransfer.setImageDrawable(AppCompatResources.getDrawable(binding.iconTransfer.context, R.drawable.icon_transfer))
                        }
                        TypeConcept.WITHDRAW.typeText -> {
                            amount.setTextColor(binding.amount.context.getColor(R.color.red))
                            iconTransfer.setImageDrawable(AppCompatResources.getDrawable(binding.iconTransfer.context, R.drawable.icon_withdraw))
                        }
                    }
                }
            }

    }
}