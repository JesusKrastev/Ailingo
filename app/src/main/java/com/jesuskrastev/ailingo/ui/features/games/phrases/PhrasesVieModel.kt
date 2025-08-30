package com.jesuskrastev.ailingo.ui.features.games.phrases

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesuskrastev.ailingo.BuildConfig
import com.jesuskrastev.ailingo.models.Phrase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class PhrasesVieModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow<PhrasesState>(PhrasesState())
    val state: StateFlow<PhrasesState> = _state
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.apiKey
    )

    init {
        loadPhrases()
    }

    private fun loadPhrases() {
        viewModelScope.launch {
            val response = generativeModel.generateContent(
                content {
                    text(
                        """
                        Generate a JSON array of 5 objects for a fill-in-the-blank English exercise.
                        Each object must include:
                        - "prefix": the part before the missing word
                        - "suffix": the part after the missing word
                        - "missingWord": the correct word that completes the sentence
                        - "options": an array of 4 words (including the correct one in a random position)
                        Return ONLY the JSON array in a single line, without Markdown, without code fences, and without extra text.
                        Example:
                        [{"prefix":"I","suffix":"to school","missingWord":"go","options":["go","goes","going","gone"]}]
                        """.trimIndent()
                    )
                }
            )

            try {
                val phrases = response?.text?.let { Json.decodeFromString<List<Phrase>>(it) } ?: emptyList()
                _state.value = PhrasesState(phrases = phrases.map { it.toPhraseState() })
            } catch(e: Exception) {
                Log.d("PhrasesViewModel", "Error decoding JSON: ${e.message}")
            }
        }
    }

    fun onEvent(event: PhrasesEvent) {
        when (event) {
            is PhrasesEvent.OnOptionSelected -> {
                val phraseIndex = event.phraseIndex
                val selectedOption = event.option
                val updatedPhrases = _state.value.phrases.toMutableList()
                val currentPhrase = updatedPhrases[phraseIndex].copy(
                    selectedOption = selectedOption,
                    isCorrect = selectedOption == updatedPhrases[phraseIndex].missingWord,
                )

                updatedPhrases[phraseIndex] = currentPhrase
                _state.value = _state.value.copy(phrases = updatedPhrases)
            }

            is PhrasesEvent.OnCheckClicked -> {
                val phraseIndex = event.phraseIndex
                val updatedPhrases = _state.value.phrases.toMutableList()
                val currentPhrase = updatedPhrases[phraseIndex]
                val updatedPhrase = updatedPhrases[phraseIndex].copy(
                    isAnswered = true,
                )
                updatedPhrases[phraseIndex] = updatedPhrase
                _state.value = _state.value.copy(phrases = updatedPhrases)
            }
        }
    }
}