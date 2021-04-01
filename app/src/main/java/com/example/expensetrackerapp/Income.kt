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
import com.example.expensetrackerapp.viewmodel.IncomeViewModel
import com.example.expensetrackerapp.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_dash_board.view.*
import kotlinx.android.synthetic.main.fragment_income.*
import kotlinx.android.synthetic.main.fragment_income.view.*


class Income : Fragment() {
    lateinit var viewModel: IncomeViewModel
    var progressDialog:ProgressDialog?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_income, container, false)
        viewModel=ViewModelProvider(this).get(IncomeViewModel::class.java)
        setUpDialog()
        viewModel.getIncome()
        viewModel.getIncomeList()
        viewModel.incomeList.observe(viewLifecycleOwner, Observer {arrayList->
            val adapter=TransactionAdapter(arrayList)
            rvIncome.adapter=adapter
            adapter.notifyDataSetChanged()

        })
        viewModel.incomeValue.observe(viewLifecycleOwner, Observer {value->
            income.setText("+Rs. "+value.toString())

        })
        return view
    }
    fun setUpDialog(){
        progressDialog= ProgressDialog(requireContext())
        progressDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog!!.setCancelable(false)
        viewModel.loadingValue.observe(this, Observer { isShow->
            if (isShow){
                progressDialog!!.show()
                progressDialog!!.setContentView(R.layout.progress_layout)
                Log.d("DONE","YES")
            }else{
                progressDialog!!.hide()
                Log.d("DONE","NO")
            }

        })
    }
}