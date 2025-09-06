package com.jesuskrastev.ailingo.ui.features.vocabulary

data class VocabularyState(
    val definitions: List<DefinitionState> = emptyList(),
    val isGenerating: Boolean = false,
    val isLoading: Boolean = false,
)