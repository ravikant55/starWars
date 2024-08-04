package com.example.starwars.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.starwars.screens.MatchesScreen
import com.example.starwars.screens.PlayersScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Players") {
        composable("Players") {
            PlayersScreen(navController = navController)
        }
        composable(
            "Matches/{name}/{id}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("id") {type = NavType.IntType }
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern = "android-app://androidx.navigation/Matches/{name}/{id}"
            })
        ) { backStackEntry ->
            MatchesScreen(
                name = backStackEntry.arguments?.getString("name") ?: "",
                id = backStackEntry.arguments?.getInt("id") ?: -1,
                navController
            )
        }
    }
}

@Composable
fun getTitleForRoute(navController: NavHostController): String {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return when (currentRoute) {
        "Players" -> "Star Wars Blaster Tournament"
        "Matches/{name}" -> "Player Details"
        else -> "Star Wars Blaster Tournament"
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(
    title: String,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content(paddingValues)
        }
    }
}*/
