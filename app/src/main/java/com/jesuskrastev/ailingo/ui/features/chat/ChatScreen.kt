package com.jesuskrastev.ailingo.ui.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun UserMessage(
    modifier: Modifier = Modifier,
    message: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = message,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun AssistantMessage(
    modifier: Modifier = Modifier,
    message: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 0.dp,
                bottomEnd = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = message.trim(),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun ChatList(
    modifier: Modifier = Modifier,
    messages: List<MessageState>,
    listState: LazyListState,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(messages) { message ->
            when {
                message.isFromUser ->
                    UserMessage(
                        message = message.text
                    )
                else ->
                    AssistantMessage(
                        message = message.text
                    )
            }
        }
    }
}

@Composable
fun ChatContent(
    modifier: Modifier = Modifier,
    state: ChatState,
    listState: LazyListState,
) {
    ChatList(
        modifier = modifier,
        messages = state.messages,
        listState = listState,
    )
}

@Composable
fun SendButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(OutlinedTextFieldDefaults.MinHeight)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.padding(horizontal = 10.dp),
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ChatInput(
    modifier: Modifier = Modifier,
    prompt: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = prompt,
        onValueChange = { text ->
            if (text.length <= 2000) onChange(text)
        },
        shape = CircleShape,
        singleLine = true,
        trailingIcon = {
            if (prompt.isNotEmpty()) {
                IconButton(
                    onClick = { onChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = "Mensaje",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    )
}

@Composable
fun ChatActions(
    modifier: Modifier = Modifier,
    isThinking: Boolean,
    prompt: String,

    onChangePrompt: (String) -> Unit,
    onSend: () -> Unit
) {
    val focusManager: FocusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ChatInput(
            modifier = Modifier.weight(1f),
            prompt = prompt,
            onChange = onChangePrompt,
        )
        SendButton(
            enabled = prompt.isNotEmpty() && !isThinking,
            onClick = {
                onSend()
                focusManager.clearFocus()
            }
        )
    }
}

@Composable
fun ChatBottomAppBar(
    modifier: Modifier = Modifier,
    prompt: String,
    isThinking: Boolean,
    onEvent: (ChatEvent) -> Unit,
) {
    ChatActions(
        modifier = Modifier.padding(horizontal = 16.dp),
        prompt = prompt,
        isThinking = isThinking,
        onChangePrompt = { onEvent(ChatEvent.OnPromptChange(it)) },
        onSend = { onEvent(ChatEvent.OnPromptSend) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    state: ChatState,
    onEvent: (ChatEvent) -> Unit,
) {
    val listState: LazyListState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            val lastIndex = state.messages.lastIndex
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lastIndex
        }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = state.role,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            )
        },
        bottomBar = {
            ChatBottomAppBar(
                prompt = state.prompt,
                isThinking = state.isThinking,
                onEvent = onEvent,
            )
        }
    ) { paddingValues ->
        ChatContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            listState = listState,
        )
    }
}