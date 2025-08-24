package com.jesuskrastev.ailingo.ui.features.home

data class HomeState(
    val term: TermState = TermState(),
    val isLoading: Boolean = true,
)