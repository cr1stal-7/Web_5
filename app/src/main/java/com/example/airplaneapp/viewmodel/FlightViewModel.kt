package com.example.airplaneapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airplaneapp.data.Flight
import com.example.airplaneapp.repository.FlightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class FlightUiState {
    object Loading : FlightUiState()
    data class Success(val flights: List<Flight>) : FlightUiState()
    data class Error(val message: String) : FlightUiState()
}

class FlightViewModel(
    private val repository: FlightRepository = FlightRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<FlightUiState>(FlightUiState.Loading)
    val uiState: StateFlow<FlightUiState> = _uiState

    fun fetchFlights(status: String? = null, destination: String? = null) {
        viewModelScope.launch {
            _uiState.value = FlightUiState.Loading
            try {
                val flights = repository.getAllFlights(status, destination)
                _uiState.value = FlightUiState.Success(flights)
            } catch (e: Exception) {
                _uiState.value = FlightUiState.Error(e.localizedMessage ?: "Произошла ошибка")
            }
        }
    }
}