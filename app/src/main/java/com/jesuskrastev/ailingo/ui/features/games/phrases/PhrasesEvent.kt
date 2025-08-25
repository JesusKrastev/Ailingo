package com.jesuskrastev.ailingo.ui.features.games.phrases

interface PhrasesEvent {
    data class OnOptionSelected(val phraseIndex: Int, val option: String): PhrasesEvent
    data class OnCheckClicked(val phraseIndex: Int) : PhrasesEvent
}