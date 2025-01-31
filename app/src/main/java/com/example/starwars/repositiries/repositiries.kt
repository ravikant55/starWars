package com.example.starwars.repositiries

import com.example.starwars.data.MatchesData
import com.example.starwars.data.PlayersData
import com.example.starwars.network.MatchService
import com.example.starwars.network.PlayerService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryPlayerList @Inject constructor(private val apiService: PlayerService) {
    suspend fun getPlayersList(): PlayersData = apiService.getPlayers()


}

class RepositoryMatchList @Inject constructor(private val apiService: MatchService) {
    suspend fun getMatchesList(): MatchesData = apiService.getMatches()
}
