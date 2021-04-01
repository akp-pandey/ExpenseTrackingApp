package com.example.expensetrackerapp

data class Transaction(val title:String,val amount:String,val transactionType:String,val tags:String,val date:String,val note:String)

fun getTransactionInfo():ArrayList<Transaction>{
    return arrayListOf(
        Transaction("Cashback Offers","200","Income","Entertainment","Oct 21,2021",""),
        Transaction("Cashback Offers","200","Income","Entertainment","Oct 21,2021",""),
        Transaction("Cashback Offers","200","Income","Entertainment","Oct 21,2021",""),
        Transaction("Cashback Offers","200","Income","Entertainment","Oct 21,2021",""),
        Transaction("Cashback Offers","200","Income","Entertainment","Oct 21,2021",""),
        Transaction("Cashback Offers","200","Income","Entertainment","Oct 21,2021",""),
        Transaction("Cashback Offers","200","Income","Entertainment","Oct 21,2021",""),
        Transaction("Cashback Offers","200","Income","Entertainment","Oct 21,2021","")
    )
}