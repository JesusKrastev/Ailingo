package com.jesuskrastev.ailingo.ui.features.writing

data class WritingState(
    val topic: String = "",
    val text: String = "",
    val isChecking: Boolean = false,
    val feedback: WritingFeedbackState = WritingFeedbackState(),
)