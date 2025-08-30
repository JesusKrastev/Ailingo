package com.jesuskrastev.ailingo.ui.features.games.order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesuskrastev.ailingo.BuildConfig
import com.jesuskrastev.ailingo.models.ShuffledPhrase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow<OrderState>(OrderState())
    val state: StateFlow<OrderState> = _state
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.apiKey,
    )

    init {
        loadPhrases()
    }

    private fun loadPhrases() {
        viewModelScope.launch {
            _state.value = OrderState(isLoading = true)
            val response = generativeModel.generateContent(
                content {
                    text(
                        """
                        Generate a JSON array of 5 objects for a phrase ordering game.
                        Each object must include:
                        - "words": an array of the phrase's words in a random order
                        - "orderedWords": an array of the same words in the correct order
                        The phrases should be English sentences (4 to 6 words).
                        Return ONLY the JSON array in a single line, without Markdown, without code fences, and without extra text.
                        Example:
                        [{"words":["school","go","to","I"],"orderedWords":["I","go","to","school"]]
                        """.trimIndent()
                    )
                }
            )

            try {
                 val shuffledPhrases = response?.text?.let { Json.decodeFromString<List<ShuffledPhrase>>(it) } ?: emptyList()
                 _state.value = OrderState(
                     shuffledPhrases = shuffledPhrases.map { it.toShuffledPhraseState() },
                     isLoading = false,
                 )
            } catch(e: Exception) {
                Log.d("PhrasesViewModel", "Error decoding JSON: ${e.message}")
            }
        }
    }

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.OnAddWord -> {
                val word = event.word
                val phrases = _state.value.shuffledPhrases.toMutableList()
                val phrase = phrases[event.phraseIndex]

                val newSelected = if (phrase.selectedWords.contains(word)) {
                    phrase.selectedWords - word
                } else {
                    phrase.selectedWords + word
                }

                phrases[event.phraseIndex] = phrase.copy(selectedWords = newSelected)
                _state.value = _state.value.copy(shuffledPhrases = phrases)
            }

            is OrderEvent.OnRemoveWord -> {
                val word = event.word
                val phrases = _state.value.shuffledPhrases.toMutableList()
                val phrase = phrases[event.phraseIndex]

                val newSelected = phrase.selectedWords - word

                phrases[event.phraseIndex] = phrase.copy(selectedWords = newSelected)
                _state.value = _state.value.copy(shuffledPhrases = phrases)
            }

            is OrderEvent.OnCheckClicked -> {
                val phraseIndex = event.phraseIndex
                val updatedPhrases = _state.value.shuffledPhrases.toMutableList()
                val currentPhrase = updatedPhrases[phraseIndex].copy(
                    isAnswered = true,
                    isCorrect = updatedPhrases[phraseIndex].selectedWords == updatedPhrases[phraseIndex].orderedWords,
                )
                updatedPhrases[phraseIndex] = currentPhrase
                _state.value = _state.value.copy(shuffledPhrases = updatedPhrases)
            }
        }
    }
}