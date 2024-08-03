package com.example.starwars.network

import com.example.starwars.data.PlayersData
import retrofit2.http.GET

interface PlayerService {
    @GET("IKQQ")
    suspend fun getPlayers(): PlayersData
}