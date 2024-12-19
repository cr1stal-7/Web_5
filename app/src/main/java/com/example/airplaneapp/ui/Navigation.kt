package com.example.airplaneapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.airplaneapp.ui.screens.FlightDetailScreen
import com.example.airplaneapp.ui.screens.FlightListScreen
import com.example.airplaneapp.ui.screens.HomeScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("flights") {
            FlightListScreen(navController)
        }
        composable(
            "flights/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            FlightDetailScreen(navController, flightId = id)
        }
    }
}