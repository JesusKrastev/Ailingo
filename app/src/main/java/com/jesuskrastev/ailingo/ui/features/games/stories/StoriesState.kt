package com.jesuskrastev.ailingo.ui.features.games.stories

data class StoriesState(
    val isLoading: Boolean = false,
    val stories: List<StoryState> = emptyList(),
)