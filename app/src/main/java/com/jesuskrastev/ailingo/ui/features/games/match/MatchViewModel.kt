package com.jesuskrastev.ailingo.ui.features.games.match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesuskrastev.ailingo.BuildConfig
import com.jesuskrastev.ailingo.models.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<MatchState>(MatchState())
    val state: StateFlow<MatchState> = _state
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-pro",
        apiKey = BuildConfig.apiKey
    )

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            val response = generativeModel.generateContent(
                content {
                    text(
                        """
                        Generate a JSON array containing exactly 10 inner arrays.
                        Each inner array must contain exactly 8 objects: 4 original words and their 4 corresponding translations order randomly.
                        
                        Each object must follow this model:  
                        - "id": a unique string identifier for the word 
                        - "text": the word in the original language 
                        - "translationId": the unique string identifier of its correct translation
                        - "isTranslation": a boolean indicating if this object is a translation (true for translation, false for original word)
                        
                        Shuffle the order of the outr arrays, and also shuffle the position of words inside each pair randomly.
                        Return ONLY the JSON array in a single line, without Markdown, without code fences, and without extra text.
                        
                        Example:  
                        [[{"id":"1","text":"cat","translationId":"6","isTranslation":false},{"id":"6","text":"gato","translationId":"1","isTranslation":true}]]
                        """.trimIndent()
                    )
                }
            )
            Log.d("MatchViewModel", "Response: ${response?.text}")

            try {
                val words = response?.text?.let { Json.decodeFromString<List<List<Word>>>(it) } ?: emptyList()
                _state.value = MatchState(words = words.map { it.map { it.toWordState() } })
            } catch(e: Exception) {
                Log.d("MatchViewModel", "Error decoding JSON: ${e.message}")
            }
        }
    }

    private fun onUnmatch(
        pageIndex: Int,
        selectedWord: WordState
    ) {
        val words = _state.value.words[pageIndex]
        val otherWord = words.find { it.id == selectedWord.selectedId }

        if (otherWord != null) {
            val updatedSublist = words.map { word ->
                when (word.id) {
                    selectedWord.id, otherWord.id -> word.copy(
                        isSelected = false,
                        isMatched = false,
                        selectedId = "",
                    )
                    else -> word
                }
            }

            val updatedWords = _state.value.words.toMutableList().apply {
                this[pageIndex] = updatedSublist
            }

            _state.value = _state.value.copy(words = updatedWords)
        }
    }


    private fun onMatch(
        pageIndex: Int,
        translatedWord: WordState
    ) {
        val words = _state.value.words[pageIndex]
        val selectedOriginal = words.find { it.isSelected && !it.isTranslation }
        val selectedTranslation = translatedWord

        if (selectedOriginal != null) {
            val updatedSublist = words.map { word ->
                when (word.id) {
                    selectedOriginal.id -> word.copy(
                        isMatched = true,
                        isSelected = false,
                        selectedId = selectedTranslation.id
                    )
                    selectedTranslation.id -> word.copy(
                        isMatched = true,
                        isSelected = false,
                        selectedId = selectedOriginal.id
                    )
                    else -> word
                }
            }
            val updatedWords = _state.value.words.toMutableList().apply {
                this[pageIndex] = updatedSublist
            }

            _state.value = _state.value.copy(words = updatedWords)
        }
    }

    private fun onSelect(
        selectedWord: WordState,
        pageIndex: Int,
    ) {
        val words = _state.value.words[pageIndex]
        val updatedSublist = words.map { word ->
            word.copy(isSelected = word.id == selectedWord.id)
        }
        val updatedWords = _state.value.words.toMutableList().apply {
            this[pageIndex] = updatedSublist
        }

        _state.value = _state.value.copy(words = updatedWords)
    }

    fun onEvent(event: MatchEvent) {
        when(event) {
            is MatchEvent.OnMatchWord -> {
                if(event.word.isMatched)
                    onUnmatch(
                        selectedWord = event.word,
                        pageIndex = event.pageIndex,
                    )
                else
                    onMatch(
                        translatedWord = event.word,
                        pageIndex = event.pageIndex,
                    )
            }

            is MatchEvent.OnSelectWord -> {
                if(event.word.isMatched)
                    onUnmatch(
                        selectedWord = event.word,
                        pageIndex = event.pageIndex,
                    )
                else
                onSelect(
                    selectedWord = event.word,
                    pageIndex = event.pageIndex,
                )
            }

            is MatchEvent.OnCheckClicked -> {
                val pageIndex = event.pageIndex
                val updatedWords = _state.value.words.toMutableList()
                val currentWords = updatedWords[pageIndex].map {
                    it.copy(
                        isCorrect = it.selectedId == it.translationId,
                        isAnswered = true
                    )
                }

                updatedWords[pageIndex] = currentWords
                _state.value = _state.value.copy(words = updatedWords)
            }
        }
    }
}