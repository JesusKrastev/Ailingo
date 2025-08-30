package com.jesuskrastev.ailingo.ui.features.games.match

sealed interface MatchEvent {
    data class OnMatchWord(val word: WordState, val pageIndex: Int) : MatchEvent
    data class OnSelectWord(val word: WordState, val pageIndex: Int) : MatchEvent
    data class OnCheckClicked(val pageIndex: Int) : MatchEvent
}