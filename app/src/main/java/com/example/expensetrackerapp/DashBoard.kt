package com.example.expensetrackerapp

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.expensetrackerapp.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_dash_board.*
import kotlinx.android.synthetic.main.fragment_dash_board.view.*

class DashBoard : Fragment() {
   lateinit var viewModel: SharedViewModel
    var progressDialog: ProgressDialog?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_dash_board, container, false)
        viewModel=ViewModelProvider(this).get(SharedViewModel::class.java)
        setUpDialog()
        viewModel.getData()
        viewModel.getBalance()
        viewModel.getExpense()
        viewModel.getIncome()
        viewModel.dashBoardInfo.observe(viewLifecycleOwner, Observer {arraylist->
            val adapter=TransactionAdapter(arraylist)
            view.rvTransaction.adapter=adapter
            adapter.notifyDataSetChanged()
        })



        viewModel.incomeVal.observe(viewLifecycleOwner, Observer { income->
            tvIncome.setText("+Rs."+income.toString())
        })
        viewModel.expenseVal.observe(viewLifecycleOwner, Observer {expense->
           tvExpense.setText("-Rs "+expense.toString())
        })
        viewModel.totalBalance.observe(viewLifecycleOwner, Observer {balance->
            tvBalance.setText("Rs. "+balance.toString())
        })

        return view
    }

    private fun setUpDialog() {
            progressDialog= ProgressDialog(requireActivity())
            progressDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setCancelable(false)
            viewModel.loadingValue.observe(this, Observer { isShow->
                if (isShow){
                    progressDialog!!.show()
                    progressDialog!!.setContentView(R.layout.progress_layout)
                }
                else{
                    progressDialog!!.hide()
                }

            })
    }


}


