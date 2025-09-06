package com.jesuskrastev.ailingo.ui.features.vocabulary.flashcards

sealed interface FlashcardsEvent {
    data object OnSwipeLeft: FlashcardsEvent
    data object OnSwipeRight: FlashcardsEvent
}