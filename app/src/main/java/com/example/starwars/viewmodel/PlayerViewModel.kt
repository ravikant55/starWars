package com.example.starwars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.starwars.data.MatchDataItem
import com.example.starwars.data.MatchesData
import com.example.starwars.data.PlayersData
import com.example.starwars.repositiries.RepositoryMatchList
import com.example.starwars.repositiries.RepositoryPlayerList
import java.io.IOException

sealed interface PlayersUiState {
    data class Success(val playersData: PlayersData, val matchesData: MatchesData) : PlayersUiState
    object Error : PlayersUiState
    object Loading : PlayersUiState
}

@HiltViewModel
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PlayerViewModel @Inject constructor(
    private val playersRepository: RepositoryPlayerList,
    private val matchesRepository: RepositoryMatchList
) : ViewModel() {

    // Using MutableState to encapsulate state management
    private val _playersUiState: MutableState<PlayersUiState> = mutableStateOf(PlayersUiState.Loading)
    val playersUiState: PlayersUiState by _playersUiState

    // Cached list of all matches
    private var allMatches: List<MatchDataItem> = emptyList()

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            _playersUiState.value = PlayersUiState.Loading
            try {
                // Fetch players and matches data
                val playersData = playersRepository.getPlayersList()
                val matchesData = matchesRepository.getMatchesList()

                // Cache all matches for filtering
                allMatches = matchesData

                // Update UI state with success
                _playersUiState.value = PlayersUiState.Success(playersData, matchesData)
            } catch (e: IOException) {
                _playersUiState.value = PlayersUiState.Error
            } catch (e: HttpException) {
                _playersUiState.value = PlayersUiState.Error
            }
        }
    }

    // Function to get filtered matches based on player id
    fun getMatchesForPlayerId(id: Int): List<MatchDataItem> {
        return allMatches.filter { match ->
            match.player1?.score == id || match.player2?.id == id
        }
    }

    // Function to get score color for a player in a match
    fun getScoreColorForPlayer(match: MatchDataItem, playerId: Int): Color {
        val player1Score = match.player1?.score ?: 0
        val player2Score = match.player2?.score ?: 0

        return when {
            playerId == match.player1?.id && player1Score > player2Score -> Color.Green
            playerId == match.player1?.id && player1Score < player2Score -> Color.Red
            playerId == match.player2?.id && player2Score > player1Score -> Color.Green
            playerId == match.player2?.id && player2Score < player1Score -> Color.Red
            else -> Color.White
        }
    }
}


