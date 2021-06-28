package com.thiyaga.ottsampleproject

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.thiyaga.ottsampleproject.di.KoinUtils

class Application : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        appContext =this.applicationContext
        Stetho.initializeWithDefaults(appContext)
        KoinUtils.setupKoin(appContext, listOf(KoinUtils.injectionModule))
    }

    companion object{
        lateinit var appContext: Context
        fun isNetConnected(): Boolean {
            val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            var result = false
            if (activeNetwork != null) {
                result = activeNetwork.isConnectedOrConnecting
            }
            return result
        }
    }


}