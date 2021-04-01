package com.example.expensetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.expensetrackerapp.livedata.SingleLiveEvent
import com.example.expensetrackerapp.room.TransactionDb
import com.google.firebase.firestore.FirebaseFirestore

class IncomeViewModel(application: Application):AndroidViewModel(application) {
    var firestore=FirebaseFirestore.getInstance()
    val incomeList by lazy { MutableLiveData<ArrayList<TransactionDb>>() }
    val incomeValue by lazy { MutableLiveData<Int>() }
    val expenseList by lazy { MutableLiveData<ArrayList<TransactionDb>>() }
    val expenseValue by lazy { MutableLiveData<Int>() }
    val loadingValue=SingleLiveEvent<Boolean>()
    val expenseLoadingValue=SingleLiveEvent<Boolean>()

    fun getIncomeList(){
        showDialog(true)
        val arrayList=ArrayList<TransactionDb>()
        firestore.collection("ExpenseTrack").get().addOnCompleteListener {task->
            if (task.isSuccessful){
                task.addOnSuccessListener {
                    it.forEach { value->
                        if (value["type"].toString().equals("Income")){
                            arrayList.add(
                                TransactionDb(
                                    value["title"].toString(),
                                    value["amount"].toString(),
                                    value["type"].toString(),
                                    value["tags"].toString(),
                                    value["date"].toString(),
                                    value["note"].toString())
                            )
                        }
                    }
                    showDialog(false)
                    incomeList.postValue(arrayList)
                }
            }

        }

    }

    private fun showDialog(b: Boolean) {
            loadingValue.value=b
    }

    fun getIncome(){
        firestore.collection("Income").document("vNEMgUAx6v0Okw6N4pb1").get().addOnSuccessListener {
            val income=it["income"].toString().toInt()
            incomeValue.postValue(income)
        }
    }

    fun getExpense(){
        firestore.collection("Expense").document("QOxDwoJET0Q3WqxFfOWN"
        ).get().addOnSuccessListener {
            val expense=it["expense"].toString().toInt()
            expenseValue.postValue(expense)
        }

    }
    fun getExpenseList(){
        showDialogExpense(true)
        val arrayList=ArrayList<TransactionDb>()
        firestore.collection("ExpenseTrack").get().addOnCompleteListener {task->
            if (task.isSuccessful){
                task.addOnSuccessListener {
                    it.forEach { value->
                        if (value["type"].toString().equals("Expense")){
                            arrayList.add(
                                TransactionDb(
                                    value["title"].toString(),
                                    value["amount"].toString(),
                                    value["type"].toString(),
                                    value["tags"].toString(),
                                    value["date"].toString(),
                                    value["note"].toString())
                            )
                        }
                    }
                    showDialogExpense(false)
                    expenseList.postValue(arrayList)

                }
            }

        }


    }

    private fun showDialogExpense(b: Boolean) {
                expenseLoadingValue.value=b
    }
}