package com.jesuskrastev.ailingo.ui.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesuskrastev.ailingo.BuildConfig
import com.jesuskrastev.ailingo.models.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(

): ViewModel() {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.apiKey
    )
    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state

    init {
        initConversation()
    }

    private fun initConversation() {
        viewModelScope.launch {
            val response = generativeModel.generateContent(
                content {
                    text("Return an everyday role that people can have, and only a plain string message, without JSON, code blocks, or extra text.")
                }
            )
            response?.text?.let {
                _state.value = _state.value.copy(
                    role = it,
                )
            }
            getResponse(
                prompt =
                    createQuery(
                        messages = emptyList(),
                        userPrompt = "Start the conversation as that character."
                    )
            )
        }
    }

    private fun addPrompt(
        prompt: String,
    ) {
        _state.value = _state.value.copy(
            messages = _state.value.messages + MessageState(
                text = prompt,
                isFromUser = true,
            ),
            prompt = "",
            isThinking = true,
        )
    }

    private fun addResponse(
        response: String,
    ) {
        _state.value = _state.value.copy(
            messages = _state.value.messages + MessageState(
                text = response,
                isFromUser = false,
            ),
            isThinking = false,
        )
    }

    private suspend fun getResponse(
        prompt: String
    ) = withContext(Dispatchers.IO) {
        generativeModel
            .generateContent(
                content {
                    val newPrompt =
                        createQuery(
                            messages = state.value.messages,
                            userPrompt = prompt
                        )
                    text(newPrompt)
                }
            )
            .text?.let { outputContent ->
                addResponse(outputContent)
            }
    }

    private fun createQuery(
        messages: List<MessageState>,
        userPrompt: String,
    ): String {
        val role = "Role: ${_state.value.role}."
        val instruction = "Assume an everyday role and respond as that character. Start and continue the conversation naturally, staying in character. Reply in English. Keep your response under 40 words."
        val prompt = "Prompt: $userPrompt."
        val previousMessages: String? = messages.dropLast(1).takeLast(5)
            .takeIf { it.isNotEmpty() }?.joinToString { it.text }
            ?.let { "Previous messages: $it." }

        return StringBuilder().apply {
            append(role)
            append(instruction)
            previousMessages?.let { append(previousMessages) }
            append(prompt)
        }.toString()
    }

    private fun sendPrompt(
        prompt: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addPrompt(prompt)
                getResponse(
                    prompt =
                        createQuery(
                            messages = _state.value.messages,
                            userPrompt = prompt,
                        )
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isThinking = false,
                )
            }
        }
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.OnPromptSend -> {
                sendPrompt(_state.value.prompt)
            }

            is ChatEvent.OnPromptChange -> {
                _state.value = _state.value.copy(
                    prompt = event.prompt,
                )
            }
        }
    }
}