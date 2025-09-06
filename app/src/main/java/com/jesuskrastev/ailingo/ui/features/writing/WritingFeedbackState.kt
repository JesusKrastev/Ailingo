package com.jesuskrastev.ailingo.ui.features.writing

import com.jesuskrastev.ailingo.models.WritingFeedback

data class WritingFeedbackState(
    val error: String = "",
    val suggestion: String = "",
    val correctText: String = "",
)

fun WritingFeedback.toFeedbackState(): WritingFeedbackState =
    WritingFeedbackState(
        error = error,
        suggestion = suggestion,
        correctText = correctText,
    )