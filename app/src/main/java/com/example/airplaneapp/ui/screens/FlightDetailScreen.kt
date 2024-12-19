package com.example.airplaneapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.airplaneapp.R
import com.example.airplaneapp.data.Flight
import com.example.airplaneapp.repository.FlightRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



@Composable
fun FlightDetailScreen(navController: NavController, flightId: Int) {
    val repository = FlightRepository()
    var flight by remember { mutableStateOf<Flight?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Используем LaunchedEffect без дополнительного coroutineScope.launch
    LaunchedEffect(flightId) {
        try {
            val fetchedFlight = withContext(Dispatchers.IO) {
                repository.getFlightById(flightId)
            }
            flight = fetchedFlight
        } catch (e: Exception) {
            error = e.localizedMessage ?: "Произошла ошибка при загрузке данных"
        } finally {
            isLoading = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ошибка: $error",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
            flight != null -> {
                flight?.let { flight ->
                    FlightDetailContent(flight = flight, navController = navController)
                }
            }
            else -> {
                // Неожиданное состояние
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Неизвестное состояние",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun FlightDetailContent(flight: Flight, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Подробная информация о полёте",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current
        // Удаляем расширение и приводим к нижнему регистру
        val imageName = flight.image.substringBeforeLast('.').lowercase()
        val imageResId = remember(imageName) {
            context.resources.getIdentifier(imageName, "drawable", context.packageName)
        }

        val painter = if (imageResId != 0) {
            painterResource(id = imageResId)
        } else {
            // Используйте изображение-заглушку, если оригинальное изображение не найдено
            painterResource(id = R.drawable.placeholder_image)
        }

        Image(
            painter = painter,
            contentDescription = flight.destination,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Место назначения: ${flight.destination}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Время: ${flight.time}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Статус: ${flight.status}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("flights") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Вернуться к списку рейсов")
        }
    }
}
