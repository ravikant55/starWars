package com.example.starwars.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.starwars.commonScreen.ErrorScreen
import com.example.starwars.commonScreen.LoadingScreen
import com.example.starwars.data.PlayersData
import com.example.starwars.data.PlayersDataItem
import com.example.starwars.viewmodel.PlayerViewModel
import com.example.starwars.viewmodel.PlayersUiState

@Composable
fun PlayersScreen(players : PlayersData) {
    Log.i("Tag", "PlayersScreen: ${players[0].icon}")
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(players.toList()){ player ->
            PlayerItem(player = player)
        }
    }
}



@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PlayerList() {
    val viewModel: PlayerViewModel = hiltViewModel()
    when (val playersUiState = viewModel.playersUiState) {
        is PlayersUiState.Loading -> LoadingScreen(/*modifier = modifier.fillMaxSize()*/)
        is PlayersUiState.Success -> PlayersScreen(
             playersUiState.playersData
        )

        is PlayersUiState.Error -> ErrorScreen(
            retryAction = { /*TODO*/ }
        )
    }
}




@Composable
fun PlayerItem(player: PlayersDataItem) {
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
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(player.icon)
                    .crossfade(true)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = "Player Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            player.name?.let {
                Text(text = it)
            }
        }
    }
}