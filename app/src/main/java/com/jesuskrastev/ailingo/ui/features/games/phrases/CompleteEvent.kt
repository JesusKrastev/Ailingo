package com.jesuskrastev.ailingo.ui.features.games.phrases

interface CompleteEvent {
    data class OnOptionSelected(val phraseIndex: Int, val option: String): CompleteEvent
    data class OnCheckClicked(val phraseIndex: Int) : CompleteEvent
}