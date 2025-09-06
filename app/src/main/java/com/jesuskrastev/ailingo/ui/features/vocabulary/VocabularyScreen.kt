package com.jesuskrastev.ailingo.ui.features.vocabulary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.jesuskrastev.ailingo.ui.features.vocabulary.components.Definition
import com.jesuskrastev.ailingo.ui.navigation.Destination
import com.jesuskrastev.ailingo.ui.navigation.FlashcardsRoute

@Composable
fun DefinitionList(
    modifier: Modifier = Modifier,
    definitions: List<DefinitionState>,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(definitions) { definition ->
            Definition(
                text = definition.text,
                translation = definition.translation,
                isLearned = definition.isLearned,
            )
        }
    }
}

@Composable
fun VocabularyContent(
    modifier: Modifier = Modifier,
    state: VocabularyState,
    onEvent: (VocabularyEvent) -> Unit,
    onNavigateTo: (Destination) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DefinitionList(
            modifier = Modifier.weight(1f),
            definitions = state.definitions,
        )
        Summary(
            count = state.definitions.count { it.isLearned },
            isGenerating = state.isGenerating,
            onGenerate = { onEvent(VocabularyEvent.OnGenerateDefinitions) },
            onLearn = { onNavigateTo(FlashcardsRoute) },
        )
    }
}

@Composable
fun Summary(
    modifier: Modifier = Modifier,
    count: Int,
    isGenerating: Boolean,
    onGenerate: () -> Unit,
    onLearn: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Resumen del progreso",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = buildAnnotatedString {
                append("Has aprendido un total de")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    append(" $count palabras y frases ")
                }
                append("hasta ahora.")
            },
            textAlign = TextAlign.Center,
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = onGenerate,
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if(isGenerating)
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                else
                    Text(
                        text = "Generar definiciones",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                    )
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = onLearn,
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Continuar aprendiendo",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabularyTopAppBar(
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text("Vocabulario")
        },
    )
}

@Composable
fun VocabularyScreen(
    modifier: Modifier = Modifier,
    state: VocabularyState,
    onEvent: (VocabularyEvent) -> Unit,
    onNavigateTo: (Destination) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            VocabularyTopAppBar()
        },
    ) { paddingValues ->
        VocabularyContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent,
            onNavigateTo = onNavigateTo,
        )
    }
}