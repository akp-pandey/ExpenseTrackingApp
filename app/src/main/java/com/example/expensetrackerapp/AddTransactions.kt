package com.example.expensetrackerapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import com.example.expensetrackerapp.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.activity_add_transactions.*
import java.util.*

class AddTransactions : AppCompatActivity(),DatePickerDialog.OnDateSetListener {
    val transactionType= arrayListOf<String>("Income","Expense")
    val tagsType= arrayListOf<String>("Food","Health","Entertainment","Mobile Recharges","Others")
    lateinit var viewModel:SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transactions)
        viewModel=ViewModelProvider(this).get(SharedViewModel::class.java)
        setUp()
        saveData()

    }

    private fun saveData() {
        addTransaction.setOnClickListener {
            viewModel.saveTransaction(titleHeading.text.toString(),amount.text.toString(),transaction.selectedItem.toString(),tags.selectedItem.toString(),date.text.toString(),note.text.toString(),it,this)
        }
    }

    private fun setUp() {
        val tagsAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,tagsType)
        val adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,transactionType)
        transaction.adapter=adapter
        tags.adapter=tagsAdapter

        dateDialog.setOnClickListener {
            val calendar=Calendar.getInstance()
            val day=calendar.get(Calendar.DAY_OF_MONTH)
            val month=calendar.get(Calendar.MONTH)
            val year=calendar.get(Calendar.YEAR)
            val datePickerDialog=DatePickerDialog(this,this,year,month,day)
            datePickerDialog.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateValue= dayOfMonth.toString()+"-"+(month+1).toString()+"-"+year.toString()
        date.setText(dateValue)
    }
}