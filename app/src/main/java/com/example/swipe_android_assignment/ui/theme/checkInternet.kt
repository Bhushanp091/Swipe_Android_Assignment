package com.example.swipe_android_assignment.ui.theme

import android.content.Context
import android.net.ConnectivityManager

fun checkInternet(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}