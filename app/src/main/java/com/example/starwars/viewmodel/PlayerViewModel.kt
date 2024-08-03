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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.starwars.data.PlayersData
import com.example.starwars.repositiries.RepositoryPlayerList
import java.io.IOException

sealed interface PlayersUiState {
    data class Success(val playersData: PlayersData) : PlayersUiState
    object Error : PlayersUiState
    object Loading : PlayersUiState
}

@HiltViewModel
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PlayerViewModel @Inject constructor(
    private val repository: RepositoryPlayerList
) : ViewModel() {

    var playersUiState: PlayersUiState by mutableStateOf(PlayersUiState.Loading)
        private set

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            playersUiState = PlayersUiState.Loading
            playersUiState = try {
                Log.i("TAG", "fetchGames: b4 success")
                PlayersUiState.Success(repository.getPlayersList())
            } catch (e: IOException) {
                Log.i("TAG", "fetchGames IOException: $e")
                PlayersUiState.Error
            } catch (e: HttpException) {
                Log.i("TAG", "fetchGames HttpException: $e")
                PlayersUiState.Error
            }
            Log.i("TAG", "fetchGames: ${playersUiState.toString()}")
        }
    }
}
