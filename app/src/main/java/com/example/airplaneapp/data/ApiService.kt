package com.example.airplaneapp.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/flights")
    suspend fun getAllFlights(
        @Query("status") status: String?,
        @Query("destination") destination: String?
    ): List<Flight>

    @GET("api/flights/{id}")
    suspend fun getFlightById(@Path("id") id: Int): Flight

    // Добавьте другие методы (POST, PUT, DELETE) при необходимости
}