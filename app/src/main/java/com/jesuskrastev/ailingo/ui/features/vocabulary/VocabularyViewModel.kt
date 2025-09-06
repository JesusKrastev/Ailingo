package com.jesuskrastev.ailingo.ui.features.vocabulary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesuskrastev.ailingo.data.DefinitionRepository
import com.jesuskrastev.ailingo.models.Definition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class VocabularyViewModel @Inject constructor(
    private val repository: DefinitionRepository,
    private val gemini: GenerativeModel,
): ViewModel() {
    private val _state = MutableStateFlow(VocabularyState())
    val state: StateFlow<VocabularyState> = _state

    init {
        loadDefinitions()
    }

    private fun loadDefinitions() {
        viewModelScope.launch {
            _state.value = VocabularyState(
                isLoading = true,
            )
            repository.get().collect {
                _state.value = VocabularyState(
                    definitions = it.map { it.toDefinitionState() },
                    isLoading = false,
                )
            }
        }
    }

    private fun generateDefinitions() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isGenerating = true)
            val response = gemini.generateContent(
                content {
                    text(
                        """
                        Generate a JSON array containing exactly 5 objects.
                        Each object must follow this model:
                        
                        Each object must follow this model:
                        - "id": a unique identifier (UUID)
                        - "text": a string containing a word in English
                        - "translation": a string containing the translation of the word
                        
                        Important:
                        - The array must contain exactly 5 objects.
                        - Do not include any of the following words: ${state.value.definitions.joinToString(separator = ",") { it.text }}
                        
                        Return ONLY the JSON array in a single line, without Markdown, without code fences, and without extra text.
                        
                        Example:  
                        [{"id":"1",text":"Please","translation":"Por favor"}]
                        """.trimIndent()
                    )
                }
            )

            try {
                val definitions = response?.text?.let { Json.decodeFromString<List<Definition>>(it) } ?: emptyList()
                _state.value = VocabularyState(
                    isGenerating = false,
                )
                Log.d("VocabularyViewModel", "Generated definitions: $definitions")
                definitions.forEach { repository.insert(it) }
            } catch(e: Exception) {
                Log.d("VocabularyViewModel", "Error decoding JSON: ${e.message}")
            }
        }
    }

    fun onEvent(event: VocabularyEvent) {
        when(event) {
            is VocabularyEvent.OnGenerateDefinitions -> {
                generateDefinitions()
            }
        }
    }
}