package com.jesuskrastev.ailingo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.onboarding.OnboardingScreen
import kotlinx.serialization.Serializable

@Serializable
object OnboardingRoute : Destination

fun NavGraphBuilder.onboardingScreen() {
    composable<OnboardingRoute> {
        // val state by vm.state.collectAsStateWithLifecycle(initialValue = HomeState())

        OnboardingScreen()
    }
}