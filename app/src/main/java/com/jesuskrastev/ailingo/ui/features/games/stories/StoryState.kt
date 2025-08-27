package com.jesuskrastev.ailingo.ui.features.games.stories

import com.jesuskrastev.ailingo.models.Story

data class StoryState(
    val text: String = "",
    val title: String = "",
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctOption: String = "",
    val selectedOption: String = "",
    val isCorrect: Boolean = false,
    val isAnswered: Boolean = false,
)

fun Story.toStoryState(): StoryState =
    StoryState(
        title = title,
        text = text,
        question = question,
        options = options,
        correctOption = correctOption,
    )