package com.jesuskrastev.ailingo.ui.features.writing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesuskrastev.ailingo.models.ShuffledPhrase
import com.jesuskrastev.ailingo.models.WritingFeedback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class WritingViewModel @Inject constructor(
    private val gemini: GenerativeModel,
) : ViewModel() {
    private val _state = MutableStateFlow(WritingState())
    val state: StateFlow<WritingState> = _state

    init {
        generateTopic()
    }

    private fun generateTopic() {
        viewModelScope.launch {
            val response = gemini.generateContent(
                content {
                    text(
                        """
                        Generates a topic in English on which the user must write a text as a writing exercise.
                        Return ONLY the text in a single line, without Markdown, without code fences, and without extra text.
                        
                        Example:  
                        "Describe a memorable event in your life and how it shaped your perspective."
                        """.trimIndent()
                    )
                }
            )

            try {
                response?.text?.let {
                    _state.value = WritingState(topic = it)
                }
            } catch (e: Exception) {
                Log.d("WritingViewModel", "Error parsing response: ${e.message}")
            }
        }
    }

    private fun onChecked() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isChecking = true)
            val response = gemini.generateContent(
                content {
                    text(
                        """
                        Generate a JSON object for giving feedback on the English text: ${state.value.text}. The object must include:

                        - "error": "error": a description of the mistakes in the text in Spanish, but mentioning the incorrect phrases or parts in the language used
                        
                        - "suggestion": concrete suggestions to improve the text level in Spanish, but giving the examples or improvements in the language used
                        
                        - "correctText": the full corrected text in English
                        
                        Return ONLY the JSON object in a single line, without Markdown, without code fences, and without extra text.
                        
                        Example:
                        {"error":"Incorrect verb tense","suggestion":"Use present simple instead of past","correctText":"I go to school every day"}
                        """.trimIndent()
                    )
                }
            )

            try {
                val feedback = response?.text?.let { Json.decodeFromString<WritingFeedback>(it) } ?: WritingFeedback()
                _state.value = _state.value.copy(
                    isChecking = false,
                    feedback = feedback.toFeedbackState(),
                )
            } catch(e: Exception) {
                Log.d("WritingViewModel", "Error decoding JSON: ${e.message}")
            }
        }
    }

    fun onEvent(event: WritingEvent) {
        when (event) {
            is WritingEvent.OnCheckedClicked -> {
                onChecked()
            }
            is WritingEvent.OnTextChanged -> {
                _state.value = _state.value.copy(text = event.text)
            }
        }
    }
}