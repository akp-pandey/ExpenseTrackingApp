package com.example.expensetrackerapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetrackerapp.databinding.TransactionLayoutBinding
import com.example.expensetrackerapp.room.TransactionDb
import kotlinx.android.synthetic.main.transaction_layout.view.*

class TransactionAdapter(val transactionInfo: ArrayList<TransactionDb>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    class TransactionViewHolder(val binding: TransactionLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: TransactionDb) {
            binding.run {
                this.transaction=transaction
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding=
            TransactionLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TransactionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return transactionInfo.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactionInfo[position])
        if(transactionInfo[position].transactionType.equals("Expense")){
            holder.itemView.amountColor.setTextColor(Color.RED)
        }
    }

}
