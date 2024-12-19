package com.example.airplaneapp.repository

import com.example.airplaneapp.data.Flight
import com.example.airplaneapp.data.RetrofitInstance

class FlightRepository {
    suspend fun getAllFlights(status: String? = null, destination: String? = null): List<Flight> {
        return RetrofitInstance.api.getAllFlights(status, destination)
    }

    suspend fun getFlightById(id: Int): Flight {
        return RetrofitInstance.api.getFlightById(id)
    }
}
