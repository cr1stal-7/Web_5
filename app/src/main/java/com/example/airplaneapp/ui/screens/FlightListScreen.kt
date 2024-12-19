package com.example.airplaneapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.airplaneapp.viewmodel.FlightUiState
import com.example.airplaneapp.viewmodel.FlightViewModel
import com.example.airplaneapp.data.Flight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.airplaneapp.R

@Composable
fun FlightListScreen(navController: NavController, viewModel: FlightViewModel = viewModel()) {
    var statusFilter by remember { mutableStateOf("Все") }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(statusFilter, searchQuery.text) {
        val query = if (searchQuery.text.isBlank()) null else searchQuery.text
        val status = if (statusFilter == "Все") null else statusFilter
        viewModel.fetchFlights(status, query)
    }

    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Список рейсов", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Поисковое поле
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Поиск по пункту назначения...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Фильтр по статусу
            var expanded by remember { mutableStateOf(false) }
            Box {
                OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Фильтр: $statusFilter")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf("Все", "В пути", "Задержан", "Отмененный").forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status) },
                            onClick = {
                                statusFilter = status
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (uiState) {
                is FlightUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is FlightUiState.Success -> {
                    val flights = (uiState as FlightUiState.Success).flights
                    LazyColumn {
                        items(flights) { flight ->
                            FlightItem(flight = flight, onClick = {
                                navController.navigate("flights/${flight.id}")
                            })
                            Divider()
                        }
                    }
                    Text(text = "Общее количество рейсов: ${flights.size}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 8.dp))
                }
                is FlightUiState.Error -> {
                    val message = (uiState as FlightUiState.Error).message
                    Text(text = "Ошибка: $message", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun FlightItem(flight: com.example.airplaneapp.data.Flight, onClick: () -> Unit) {
    val context = LocalContext.current
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = flight.destination,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = flight.destination, style = MaterialTheme.typography.titleMedium)
            Text(text = "Время: ${flight.time}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Статус: ${flight.status}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun getImageResource(imageName: String): Int {
    val context = LocalContext.current
    val resourceName = imageName.substringBeforeLast('.') // Убираем расширение
    return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
}