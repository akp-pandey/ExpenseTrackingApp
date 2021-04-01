package com.example.expensetrackerapp.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.expensetrackerapp.AddTransactions
import com.example.expensetrackerapp.livedata.SingleLiveEvent
import com.example.expensetrackerapp.room.TransactionDb
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.exp
import kotlin.properties.Delegates

class SharedViewModel(application: Application):AndroidViewModel(application) {
    var firebaseFirestore=FirebaseFirestore.getInstance()
    val dashBoardInfo by lazy { MutableLiveData<ArrayList<TransactionDb>>() }
    val incomeVal by lazy { MutableLiveData<Int>() }
    val expenseVal by lazy { MutableLiveData<Int>() }
    val totalBalance by lazy { MutableLiveData<Int>() }
    val loadingValue=SingleLiveEvent<Boolean>()

    fun saveTransaction(
        title: String,
        amount: String,
        type: String,
        tags: String,
        date: String,
        note: String,
        it: View,
        addTransactions: AddTransactions
    ) {
        if (title.isEmpty() || amount.isEmpty() || type.isEmpty() || tags.isEmpty() || date.isEmpty() || note.isEmpty()){
            Snackbar.make(it,"All feilds are required",Snackbar.LENGTH_LONG).show()
        }else{
            Log.d("MSG",title+""+amount+""+date+""+note+""+tags+""+type)
            firebaseFirestore.collection("ExpenseTrack").add(
                hashMapOf(
                    "title" to title,
                    "amount" to amount,
                    "type" to type,
                    "tags" to tags,
                    "date" to date,
                    "note" to note
                )
            ).addOnSuccessListener {

                if (type.equals("Income")){
                    firebaseFirestore.collection("Income").document("vNEMgUAx6v0Okw6N4pb1")
                        .get().addOnSuccessListener {
                            var income=it["income"].toString().toInt()
                            income += amount.toInt()
                            firebaseFirestore.collection("Income").document("vNEMgUAx6v0Okw6N4pb1")
                                .update("income",income)
                            firebaseFirestore.collection("TotalBalance").document("FLRHQ4s1AG59dcImOWQp")
                                .get().addOnSuccessListener {
                                    var balance=it["balance"].toString().toInt()
                                    balance+=amount.toInt()
                                    firebaseFirestore.collection("TotalBalance").document("FLRHQ4s1AG59dcImOWQp")
                                        .update("balance",balance)
                                        totalBalance.postValue(balance)
                                }

                        }
                }else if (type.equals("Expense"))
                    firebaseFirestore.collection("Expense").document("QOxDwoJET0Q3WqxFfOWN")
                        .get().addOnSuccessListener {
                            var expense=it["expense"].toString().toInt()
                            expense += amount.toInt()

                            firebaseFirestore.collection("Expense").document("QOxDwoJET0Q3WqxFfOWN")
                                .update("expense",expense)
                            firebaseFirestore.collection("TotalBalance").document("FLRHQ4s1AG59dcImOWQp")
                                .get().addOnSuccessListener {
                                    var balance=it["balance"].toString().toInt()
                                    balance-=amount.toInt()
                                    firebaseFirestore.collection("TotalBalance").document("FLRHQ4s1AG59dcImOWQp")
                                        .update("balance",balance)
                                }


                }
            }

        }
        Toast.makeText(addTransactions,"Successfully added",Toast.LENGTH_LONG).show()
        addTransactions.finish()
    }
    fun getBalance(){
        firebaseFirestore.collection("TotalBalance").document("FLRHQ4s1AG59dcImOWQp").get().addOnSuccessListener {
            val balance=it["balance"].toString().toInt()
            totalBalance.postValue(balance)
        }
    }
    fun getIncome(){
        firebaseFirestore.collection("Income").document("vNEMgUAx6v0Okw6N4pb1").get().addOnSuccessListener {
            val income=it["income"].toString().toInt()
            incomeVal.postValue(income)
        }

    }
    fun getExpense(){
        firebaseFirestore.collection("Expense").document("QOxDwoJET0Q3WqxFfOWN").get().addOnSuccessListener {
            val expense=it["expense"].toString().toInt()
            expenseVal.postValue(expense)
        }

    }
    fun getData(){
        showDialog(true)
        firebaseFirestore.collection("ExpenseTrack").get().addOnCompleteListener {task->

            if (task.isSuccessful){
                val arrayList=ArrayList<TransactionDb>()
                task.addOnSuccessListener {document->

                    document.forEach { value->
                        val transactionDb=TransactionDb(value["title"].toString(),
                        value["amount"].toString(),
                        value["type"].toString(),
                        value["tags"].toString(),
                        value["date"].toString(),
                        value["note"].toString())
                        arrayList.add(transactionDb)


                    }

                }

                dashBoardInfo.postValue(arrayList)
                showDialog(false)
            }


        }



    }

    private fun showDialog(b: Boolean) {
            loadingValue.value=b
    }

}