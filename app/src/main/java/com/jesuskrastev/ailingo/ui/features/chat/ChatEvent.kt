package com.jesuskrastev.ailingo.ui.features.chat

sealed interface ChatEvent {
    data object OnPromptSend: ChatEvent
    data class OnPromptChange(val prompt: String): ChatEvent
}