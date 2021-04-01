package com.example.expensetrackerapp

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.expensetrackerapp.viewmodel.IncomeViewModel
import kotlinx.android.synthetic.main.fragment_expense.*
import kotlinx.android.synthetic.main.fragment_expense.view.*
import kotlinx.android.synthetic.main.fragment_income.*


class Expense : Fragment() {
    lateinit var viewModel:IncomeViewModel
    var progressDialog:ProgressDialog?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_expense, container, false)
        viewModel=ViewModelProvider(this).get(IncomeViewModel::class.java)
        setUpDialog()
        viewModel.getExpense()
        viewModel.getExpenseList()

        viewModel.expenseValue.observe(viewLifecycleOwner, Observer {value->
            expenseValue.setText("-Rs. "+value)
        })
        viewModel.expenseList.observe(viewLifecycleOwner, Observer { arraylist->
            val adapter=TransactionAdapter(arraylist)
            rvExpense.adapter=adapter
            adapter.notifyDataSetChanged()

        })
        return view
    }

    private fun setUpDialog() {
        progressDialog= ProgressDialog(requireContext())
        progressDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog!!.setCancelable(false)
        viewModel.expenseLoadingValue.observe(this, Observer { isShown->
            if (isShown){
                progressDialog!!.show()
                progressDialog!!.setContentView(R.layout.progress_layout)
            }else{
                progressDialog!!.hide()
            }

        })
    }

}