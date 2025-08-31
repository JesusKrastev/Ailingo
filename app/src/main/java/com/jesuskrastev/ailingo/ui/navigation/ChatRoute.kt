package com.jesuskrastev.ailingo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.chat.ChatScreen
import com.jesuskrastev.ailingo.ui.features.chat.ChatState
import com.jesuskrastev.ailingo.ui.features.chat.ChatViewModel
import kotlinx.serialization.Serializable

@Serializable
object ChatRoute : Destination

fun NavGraphBuilder.chatScreen() {
    composable<ChatRoute> {
        val vm: ChatViewModel = hiltViewModel()
        val state by vm.state.collectAsStateWithLifecycle(initialValue = ChatState())

        ChatScreen(
            state = state,
            onEvent = vm::onEvent,
        )
    }
}