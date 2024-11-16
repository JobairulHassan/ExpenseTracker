package com.example.expensetracker

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationHostScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "/splash") {
        composable(route ="/splash") {
            SplashScreen(navController)
        }
        composable(route ="/name_input") {
            InputName(navController)
        }
        composable(route ="/home") {
            HomeScreen(navController)
        }
        composable(route ="/add") {
            AddExpense(navController)
        }
        composable(route ="/show_details") {
            ShowExpenses(navController)
        }
    }
}