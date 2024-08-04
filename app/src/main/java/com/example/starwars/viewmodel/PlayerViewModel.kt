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

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            _playersUiState.value = PlayersUiState.Loading
            try {
                val playersData = playersRepository.getPlayersList()
                val matchesData = matchesRepository.getMatchesList()
                _playersUiState.value = PlayersUiState.Success(playersData, matchesData)
            } catch (e: IOException) {
                _playersUiState.value = PlayersUiState.Error
            } catch (e: HttpException) {
                _playersUiState.value = PlayersUiState.Error
            }
        }
    }
}
