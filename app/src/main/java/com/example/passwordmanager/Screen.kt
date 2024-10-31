package com.example.passwordmanager

sealed class Screen(val route:String) {
    object HomeScreen: Screen("home_screen")
    object LoginScreen: Screen("login_screen")
}