package com.example.starwars.screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.starwars.commonScreen.ErrorScreen
import com.example.starwars.commonScreen.LoadingScreen
import com.example.starwars.data.MatchDataItem
import com.example.starwars.data.MatchesData
import com.example.starwars.viewmodel.PlayerViewModel
import com.example.starwars.viewmodel.PlayersUiState

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(name: String, id: Int, navController: NavHostController) {

    Column {
        TopAppBar(
            title = { Text(text = "Player: $name") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back")
                }
            }
        )

        val viewModel: PlayerViewModel = hiltViewModel()
        when (val playersUiState = viewModel.playersUiState) {
            is PlayersUiState.Loading -> LoadingScreen(/*modifier = modifier.fillMaxSize()*/)
            is PlayersUiState.Success -> MatchesItem(
                viewModel, id
            )

            is PlayersUiState.Error -> ErrorScreen(
                retryAction = { /*TODO*/ }
            )
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
private fun MatchesItem(viewModel: PlayerViewModel, id: Int) {

    val filteredMatches = viewModel.getMatchesForPlayerId(id)
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(filteredMatches.toList()) { match ->
            MatchItem(match = match)
        }
    }
}

@Composable
private fun MatchItem(match: MatchDataItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            match.player1?.id.toString().let {
                Text(text = it)
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "${match.player1?.score.toString()} - ${match.player2?.score.toString()}" )
            Spacer(modifier = Modifier.width(16.dp))

            match.player2?.id.toString().let {
                Text(text = it)
            }
        }
    }
}




