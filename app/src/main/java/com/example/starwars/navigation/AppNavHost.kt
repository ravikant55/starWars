package com.example.starwars.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            "Matches/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "android-app://androidx.navigation/Matches/{name}"
            })
        ) { backStackEntry ->
            MatchesScreen(name = backStackEntry.arguments?.getString("name") ?: "", navController)
        }
    }
}