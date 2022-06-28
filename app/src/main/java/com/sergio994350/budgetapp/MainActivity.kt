package com.sergio994350.budgetapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var transactions: ArrayList<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactions = arrayListOf(
            Transaction("аванс", 12800.0),
            Transaction("бензин", -1100.0),
            Transaction("продукты", -1320.0),
            Transaction("запчасти для машины", -3500.0),
            Transaction("автосервис", -2200.0),
            Transaction("подарок ребенку", -1800.0)
        )

        transactionAdapter = TransactionAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this)

        recyclerview_content.apply {
            adapter = transactionAdapter
            layoutManager = linearLayoutManager
        }
        upgradeDashboard()
    }

    @SuppressLint("SetTextI18n")
    private fun upgradeDashboard() {
        val totalAmount = transactions.sumOf { it.amount }
        val budgetAmount = transactions.filter { it.amount > 0 }.sumOf { it.amount }
        val expenseAmount = totalAmount - budgetAmount

        tv_balance.text = "%.2f".format(totalAmount)
        tv_budget.text = "%.2f".format(budgetAmount)
        tv_expense.text = "%.2f".format(expenseAmount)
    }
}