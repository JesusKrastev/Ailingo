package com.jesuskrastev.ailingo.ui.features.writing

sealed interface WritingEvent {
    data class OnTextChanged(val text: String) : WritingEvent
    object OnCheckedClicked : WritingEvent
}