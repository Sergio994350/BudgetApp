package com.sergio994350.budgetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_add_transaction.*

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
            }
        }
        closeBtn.setOnClickListener {
            finish()
        }
    }
}