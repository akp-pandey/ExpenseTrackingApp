package com.example.expensetrackerapp

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.expensetrackerapp.connectivity.ConnectivityLiveData
import com.example.expensetrackerapp.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var fragment:Fragment

    val category= arrayListOf<String>("Dashboard","All Income","All Expenses")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val connectivityLiveData=ConnectivityLiveData(this)
        connectivityLiveData.observe(this, Observer { isConnected->
            if (!isConnected){
                Toast.makeText(this,"Please connect to internet",Toast.LENGTH_LONG).show()
            }
        })
        setUp()
        setUpEvents()
    }

    private fun setUpEvents() {
            fab.setOnClickListener {
               startActivity(Intent(this,AddTransactions::class.java))
            }
    }

    private fun setUp() {
        val navController=findNavController(R.id.fragment)
        bottomNavigation.setupWithNavController(navController)
    }




}