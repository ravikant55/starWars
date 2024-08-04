package com.example.starwars.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.starwars.R
import com.example.starwars.commonScreen.ErrorScreen
import com.example.starwars.commonScreen.LoadingScreen
import com.example.starwars.data.PlayersData
import com.example.starwars.data.PlayersDataItem
import com.example.starwars.viewmodel.PlayerViewModel
import com.example.starwars.viewmodel.PlayersUiState

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PlayersScreen(navController: NavHostController) {

    val viewModel: PlayerViewModel = hiltViewModel()
    when (val playersUiState = viewModel.playersUiState) {
        is PlayersUiState.Loading -> LoadingScreen(/*modifier = modifier.fillMaxSize()*/)
        is PlayersUiState.Success -> PlayersItem(
            playersUiState.playersData,
            navController
        )

        is PlayersUiState.Error -> ErrorScreen(
            retryAction = { /*TODO*/ }
        )
    }
}

@Composable
private fun PlayersItem(players: PlayersData,  navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(players.toList()) { player ->
            PlayerItem(player = player, navController)
        }
    }
}

@Composable
private fun PlayerItem(player: PlayersDataItem, navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("Matches/${player.name}/${player.id}")
            } // Pass player name as argument
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            CategoryImageFromURLWithPlaceHolder(player.icon.toString())
            Spacer(modifier = Modifier.width(16.dp))
            player.name?.let {
                Text(text = it)
            }
        }
    }
}

@Composable
fun CategoryImageFromURLWithPlaceHolder(url: String) {
    Image(
        painter = // Make sure you have a placeholder drawable
        rememberAsyncImagePainter(
            ImageRequest.Builder // Optional error drawable
                (LocalContext.current).data(data = url).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background) // Make sure you have a placeholder drawable
                error(R.drawable.ic_connection_error) // Optional error drawable
            }).build()
        ),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape) // Apply circular clipping
            .size(48.dp)
    )
}
