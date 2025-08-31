package com.jesuskrastev.ailingo.ui.features.chat

data class ChatState(
    val messages: List<MessageState> = mutableListOf(),
    var prompt: String = "",
    val isThinking: Boolean = false,
    val role: String = "",
)