package com.jesuskrastev.ailingo.ui.features.games.order

import com.jesuskrastev.ailingo.ui.features.games.phrases.PhrasesEvent

interface OrderEvent {
    data class OnAddWord(val phraseIndex: Int, val word: String) : OrderEvent
    data class OnRemoveWord(val phraseIndex: Int, val word: String) : OrderEvent
    data class OnCheckClicked(val phraseIndex: Int) : OrderEvent
}