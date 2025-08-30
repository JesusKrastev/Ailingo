package com.jesuskrastev.ailingo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jesuskrastev.ailingo.ui.features.home.HomeViewModel

@Composable
fun AilingoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val vmHome: HomeViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute,
    ) {
        homeScreen(
            vm = vmHome,
            onNavigateTo = { destination ->
                navController.navigate(destination)
            }
        )
        gamesScreen(
            onNavigateTo = { destination ->
                navController.navigate(destination)
            },
        )
        matchGameScreen()
        orderGameScreen()
        completeGameScreen()
        storiesGameScreen()
    }
}