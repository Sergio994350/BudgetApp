package com.sergio994350.budgetapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var transactions: List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactions = arrayListOf()
//        Transaction("аванс", 12800.0),
//        Transaction("бензин", -1100.0),
//        Transaction("продукты", -1320.0),
//        Transaction("запчасти для машины", -3500.0),
//        Transaction("автосервис", -2200.0),
//        Transaction("подарок ребенку", -1800.0)

        transactionAdapter = TransactionAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(
            this, AppDatabase::class.java, "transactions"
        ).build()

        recyclerview_content.apply {
            adapter = transactionAdapter
            layoutManager = linearLayoutManager
        }

        addBtn.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchAll() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()

            runOnUiThread {
                upgradeDashboard()
                transactionAdapter.setData(transactions)
            }
        }
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

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}