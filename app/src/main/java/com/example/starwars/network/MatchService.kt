package com.example.starwars.network

import com.example.starwars.data.Match
import retrofit2.http.GET

interface MatchService {
    @GET("JNYL")
    suspend fun getMatches(): List<Match>
}