package com.example.expensetrackerapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class TransactionDb(val title:String,val amount:String, val transactionType:String, val tags:String, val date:String, val note:String)
