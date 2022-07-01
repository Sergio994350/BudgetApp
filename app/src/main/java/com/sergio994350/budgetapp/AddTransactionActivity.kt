package com.sergio994350.budgetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        addTransactionBtn.setOnClickListener {
            val label: String = label_input_edit_text.text.toString()
            val descriptionText: String = description_input_edit_text.text.toString()
            val amount: Double? = amount_input_edit_text.text.toString().toDoubleOrNull()

            label_input_edit_text.addTextChangedListener {
                if (it!!.count() > 0)
                    label_input_layout.error = null
            }

            amount_input_edit_text.addTextChangedListener {
                if (it!!.count() > 0)
                    amount_input_layout.error = null
            }

            description_input_edit_text.addTextChangedListener {
                if (it!!.count() > 0)
                    description_input_layout.error = null
            }

            if (label.isEmpty()) {
                label_input_layout.error = "Введите заголовок"
            }
            if (amount == null) {
                amount_input_layout.error = "Введите сумму"
            }
            if (descriptionText.isEmpty()) {
                description_input_layout.error = "Введите описание"
            } else {
                val transaction = Transaction(0, label, amount!!, descriptionText)
                insert(transaction)
            }
        }
        closeBtn.setOnClickListener {
            finish()
        }
    }

    private fun insert(transaction: Transaction) {
        val db: AppDatabase = Room.databaseBuilder(
            this, AppDatabase::class.java, "transactions"
        ).build()

        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()
        }
    }
}