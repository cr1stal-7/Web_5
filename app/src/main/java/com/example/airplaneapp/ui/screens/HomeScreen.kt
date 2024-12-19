package com.example.airplaneapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment          // Необходим для Alignment.CenterHorizontally
import androidx.compose.ui.Modifier           // Явный импорт для Modifier
import androidx.compose.ui.unit.dp            // Необходим для dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)  // Использование dp
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Добро пожаловать в приложение SkyWay!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)  // Использование dp
            )
            Button(
                onClick = { navController.navigate("flights") },
                modifier = Modifier.align(Alignment.CenterHorizontally)  // Использование Alignment
            ) {
                Text(text = "Перейти к списку рейсов")
            }
        }
    }
}
