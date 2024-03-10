package com.example.swipe_android_assignment.util

sealed class Screen(val route:String){

    object HomeScreen:Screen("home")
    object AddProduct:Screen("add")
    object Search:Screen("search")


}