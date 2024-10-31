package com.example.passwordmanager

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passwordmanager.model.MainViewModel

@Composable
fun Navigation(viewModel: MainViewModel = viewModel(),
               navController: NavHostController = rememberNavController()){
    NavHost(
        navController= navController,
        startDestination = Screen.LoginScreen.route
    ){
        composable(Screen.LoginScreen.route){
            LoginScreen(navController)
        }


        composable(Screen.HomeScreen.route){
            HomeScreen(navController, viewModel)
        }
    }
}