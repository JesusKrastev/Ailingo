package com.jesuskrastev.ailingo.ui.features.vocabulary.flashcards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesuskrastev.ailingo.data.DefinitionRepository
import com.jesuskrastev.ailingo.ui.features.vocabulary.toDefinition
import com.jesuskrastev.ailingo.ui.features.vocabulary.toDefinitionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashcardsViewModel @Inject constructor(
    private val repository: DefinitionRepository,
): ViewModel() {
    private val _state = MutableStateFlow(FlashcardsState())
    val state: StateFlow<FlashcardsState> = _state

    init {
        loadDefinitions()
    }

    private fun loadDefinitions() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            _state.value = _state.value.copy(
                definitions = repository.getPending().map { it.toDefinitionState() },
                isLoading = false,
            )
        }
    }

    fun onEvent(event: FlashcardsEvent) {
        when (event) {
            FlashcardsEvent.OnSwipeLeft -> {
                val definitions = _state.value.definitions.toMutableList()
                val currentDefinition = definitions.removeAt(_state.value.index)
                definitions.add(currentDefinition)
                _state.value = _state.value.copy(
                    definitions = definitions,
                    index = _state.value.index + 1,
                )
            }
            FlashcardsEvent.OnSwipeRight -> {
                val currentIndex = _state.value.index
                val currentDefinition = _state.value.definitions[currentIndex]
                val updatedDefinition = currentDefinition.copy(isLearned = true)

                _state.value = _state.value.copy(
                    index = currentIndex + 1
                )
                val definitions = _state.value.definitions.toMutableList()
                definitions.removeAt(currentIndex)
                viewModelScope.launch {
                    repository.update(updatedDefinition.toDefinition())
                }
            }
        }
    }
}