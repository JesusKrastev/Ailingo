package com.jesuskrastev.ailingo.ui.features.games.stories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesuskrastev.ailingo.BuildConfig
import com.jesuskrastev.ailingo.models.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow<StoriesState>(StoriesState())
    val state: StateFlow<StoriesState> = _state
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.apiKey,
    )

    init {
        loadTexts()
    }

    private fun loadTexts() {
        viewModelScope.launch {
            val response = generativeModel.generateContent(
                content {
                    text(
                        """
                        Generate a JSON array of 5 objects for a reading comprehension game.  
                        Each object must include:  
                        - "title": a short, descriptive title for the story.
                        - "text": a short story or paragraph in English (2 to 3 sentences).  
                        - "question": a question related to the story.  
                        - "options": an array of 4 possible answers to the question.  
                        - "correctOption": the correct answer from the options array.  
                        
                        The stories should be engaging. Questions should test understanding of the story.  
                        Return ONLY the JSON array in a single line, without Markdown, without code fences, and without extra text.  
                        
                        Example:  
                        [{"text":"Alice went to the park to play with her dog.","question":"Where did Alice go?","options":["To the park","To the school","To the store","To the beach"],"correctOption":"To the park"}]
                        """.trimIndent()
                    )
                }
            )

            try {
                val stories = response?.text?.let { Json.decodeFromString<List<Story>>(it) } ?: emptyList()
                _state.value = StoriesState(stories = stories.map { it.toStoryState() })
            } catch(e: Exception) {
                Log.d("PhrasesViewModel", "Error decoding JSON: ${e.message}")
            }
        }
    }

    fun onEvent(event: StoriesEvent) {
        when (event) {
            is StoriesEvent.OnOptionSelected -> {
                val stories = _state.value.stories.toMutableList()
                val story = stories[event.storyIndex]

                stories[event.storyIndex] = story.copy(selectedOption = event.option)
                _state.value = _state.value.copy(stories = stories)
            }

            is StoriesEvent.OnCheckClicked -> {
                val storyIndex = event.storyIndex
                val updatedStories = _state.value.stories.toMutableList()
                val story = updatedStories[storyIndex]
                val currentStory = story.copy(
                    isAnswered = true,
                    isCorrect = story.selectedOption == story.correctOption
                )
                updatedStories[storyIndex] = currentStory
                _state.value = _state.value.copy(stories = updatedStories)
            }
        }
    }
}