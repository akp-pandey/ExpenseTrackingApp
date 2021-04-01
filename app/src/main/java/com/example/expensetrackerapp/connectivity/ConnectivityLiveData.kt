package com.example.expensetrackerapp.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData

class ConnectivityLiveData(val context: Context):LiveData<Boolean>() {
    private var cm=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

private val broadcastReceiver=object:BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        updateConnection()
    }
}
    fun updateConnection(){
        val activeInfo=cm.activeNetworkInfo
        postValue(activeInfo?.isConnected==true)
    }

    override fun onActive() {
        super.onActive()
        context.registerReceiver(broadcastReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(broadcastReceiver)
    }

}