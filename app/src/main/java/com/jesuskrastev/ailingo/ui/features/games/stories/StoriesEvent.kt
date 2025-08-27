package com.jesuskrastev.ailingo.ui.features.games.stories

interface StoriesEvent {
    data class OnOptionSelected(val storyIndex: Int, val option: String): StoriesEvent
    data class OnCheckClicked(val storyIndex: Int) : StoriesEvent
}