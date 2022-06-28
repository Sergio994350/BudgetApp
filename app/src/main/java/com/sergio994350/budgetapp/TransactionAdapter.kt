package com.sergio994350.budgetapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class TransactionAdapter(private val transactions: ArrayList<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    class TransactionHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label: TextView = view.findViewById(R.id.tv_label)
        val amount: TextView = view.findViewById(R.id.tv_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_layout, parent, false)
        return TransactionHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaction: Transaction = transactions[position]
        val context: Context = holder.amount.context

        if (transaction.amount >= 0) {
            holder.amount.text = "+ %.2f".format(transaction.amount)
            holder.amount.setTextColor(Color.GREEN)
        } else {
            holder.amount.text = "- %.2f".format(abs(transaction.amount))
            holder.amount.setTextColor(Color.RED)
        }
        holder.label.text = transaction.label
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}