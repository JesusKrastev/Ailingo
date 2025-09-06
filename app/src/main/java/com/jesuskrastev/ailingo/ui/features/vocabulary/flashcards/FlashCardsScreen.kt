package com.jesuskrastev.ailingo.ui.features.vocabulary.flashcards

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesuskrastev.ailingo.ui.composables.SwipeCard
import com.jesuskrastev.ailingo.ui.features.vocabulary.DefinitionState

@Composable
fun Flashcard(
    modifier: Modifier = Modifier,
    offset: Float,
    definition: String,
    translation: String,
) {
    var showTranslation by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = { showTranslation = !showTranslation }),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .graphicsLayer {
                        alpha = (offset / 200f).coerceIn(0f, 1f)
                        rotationZ = -15f
                    }
                    .padding(16.dp)
                    .graphicsLayer {
                        alpha = (offset / 200f).coerceIn(0f, 1f)
                    }
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Acertado",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .graphicsLayer {
                        alpha = (-offset / 200f).coerceIn(0f, 1f)
                        rotationZ = 15f
                    }
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.error,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Fallado",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = if(showTranslation) translation else definition,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun SwipeableCard(
    modifier: Modifier = Modifier,
    definition: String,
    translation: String,
    onSwipedLeft: () -> Unit,
    onSwipedRight: () -> Unit,
) {
    var offset by remember { mutableFloatStateOf(0f) }

    SwipeCard(
        onOffsetChange = { offset = it },
        onSwipeLeft = {
            onSwipedLeft()
        },
        onSwipeRight = {
            onSwipedRight()
        }
    ) {
        Flashcard(
            modifier = modifier.padding(16.dp),
            definition = definition,
            translation = translation,
            offset = offset
        )
    }
}

@Composable
fun SwipeCardDeck(
    modifier: Modifier = Modifier,
    index: Int,
    definitions: List<DefinitionState>,
    onSwipedLeft: () -> Unit,
    onSwipedRight: () -> Unit,
) {
    if(index < definitions.size)
        SwipeableCard(
            modifier = modifier,
            definition = definitions.getOrNull(index)?.text ?: "No definition found",
            translation = definitions.getOrNull(index)?.translation ?: "No translation found",
            onSwipedLeft = onSwipedLeft,
            onSwipedRight = onSwipedRight
        )
}

@Composable
fun FlashcardsContent(
    modifier: Modifier = Modifier,
    state: FlashcardsState,
    onEvent: (FlashcardsEvent) -> Unit,
) {
    SwipeCardDeck(
        modifier = modifier,
        onSwipedLeft = { onEvent(FlashcardsEvent.OnSwipeLeft) },
        onSwipedRight = { onEvent(FlashcardsEvent.OnSwipeRight) },
        index = state.index,
        definitions = state.definitions,
    )
}

@Composable
fun FlashcardsScreen(
    modifier: Modifier = Modifier,
    state: FlashcardsState,
    onEvent: (FlashcardsEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.statusBars,
    ) { paddingValues ->
        FlashcardsContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent,
        )
    }
}