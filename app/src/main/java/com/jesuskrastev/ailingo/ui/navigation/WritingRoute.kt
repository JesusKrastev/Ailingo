package com.jesuskrastev.ailingo.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.compose.runtime.getValue
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.writing.WritingScreen
import com.jesuskrastev.ailingo.ui.features.writing.WritingState
import com.jesuskrastev.ailingo.ui.features.writing.WritingViewModel
import kotlinx.serialization.Serializable

@Serializable
object WritingRoute : Destination

fun NavGraphBuilder.writingScreen() {
    composable<WritingRoute> {
        val vm: WritingViewModel = hiltViewModel()
        val state by vm.state.collectAsStateWithLifecycle(initialValue = WritingState())

        WritingScreen(
            state = state,
            onEvent = vm::onEvent,
        )
    }
}