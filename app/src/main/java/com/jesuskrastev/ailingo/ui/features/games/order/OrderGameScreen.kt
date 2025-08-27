package com.jesuskrastev.ailingo.ui.features.games.order

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jesuskrastev.ailingo.ui.composables.dashedBorder
import kotlinx.coroutines.launch

@Composable
fun ResultWord(
    modifier: Modifier = Modifier,
    word: String,
    isAnswered: Boolean,
    isCorrect: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                !isAnswered -> MaterialTheme.colorScheme.surfaceVariant
                isCorrect -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.errorContainer
            },
            contentColor = when {
                !isAnswered -> MaterialTheme.colorScheme.onSurfaceVariant
                isCorrect -> MaterialTheme.colorScheme.onSecondaryContainer
                else -> MaterialTheme.colorScheme.onErrorContainer
            },
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = word,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun SelectableWord(
    modifier: Modifier = Modifier,
    word: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
            contentColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = word,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun PhrasePlaceholder(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(12.dp)
            )
            .dashedBorder(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.24f),
                shape = RoundedCornerShape(12.dp),
                strokeWidth = 2.dp,
                dashLength = 8.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            fontWeight = FontWeight.Medium,
            text = "Toca alguna palabra para empezar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderedPhrase(
    modifier: Modifier = Modifier,
    shuffledPhrase: ShuffledPhraseState,
    onDeselect: (String) -> Unit,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        shuffledPhrase.selectedWords.forEachIndexed { index, word ->
            ResultWord(
                word = word,
                isCorrect = shuffledPhrase.orderedWords.getOrNull(index) == word,
                isAnswered = shuffledPhrase.isAnswered,
                onClick = { if(!shuffledPhrase.isAnswered) onDeselect(word) },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Phrase(
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit,
    shuffledPhrase: ShuffledPhraseState,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        shuffledPhrase.words.forEach { word ->
            SelectableWord(
                word = word,
                isSelected = shuffledPhrase.selectedWords.contains(word),
                onClick = { if(!shuffledPhrase.isAnswered) onSelect(word) },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShuffledPhrase(
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit,
    onDeselect: (String) -> Unit,
    shuffledPhrase: ShuffledPhraseState,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Phrase(
            onSelect = onSelect,
            shuffledPhrase = shuffledPhrase,
        )
        if (shuffledPhrase.selectedWords.isEmpty())
            PhrasePlaceholder()
        else
            OrderedPhrase(
                shuffledPhrase = shuffledPhrase,
                onDeselect = onDeselect
            )
        if(shuffledPhrase.isAnswered && !shuffledPhrase.isCorrect)
            CorrectAnswer(
                phrase = shuffledPhrase.orderedWords.joinToString(" "),
            )
    }
}

@Composable
fun ShuffledPhrasesList(
    modifier: Modifier = Modifier,
    shuffledPhrases: List<ShuffledPhraseState>,
    onSelect: (String) -> Unit,
    onDeselect: (String) -> Unit,
    pagerState: PagerState,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        userScrollEnabled = false,
    ) {
        ShuffledPhrase(
            shuffledPhrase = shuffledPhrases[it],
            onSelect = onSelect,
            onDeselect = onDeselect,
        )
    }
}

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    current: Int,
    total: Int,
    progress: Float,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Frase $current de $total",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            progress = progress,
        )
    }
}

@Composable
fun Title(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "Ordena las palabras",
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun ShuffledPhrases(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onSelect: (String) -> Unit,
    onDeselect: (String) -> Unit,
    shuffledPhrases: List<ShuffledPhraseState>,
) {
    val animatedProgress = animateFloatAsState(
        targetValue = (pagerState.currentPage + 1).toFloat() / pagerState.pageCount,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProgressBar(
            current = pagerState.currentPage + 1,
            total = pagerState.pageCount,
            progress = animatedProgress.value,
        )
        Title()
        ShuffledPhrasesList(
            shuffledPhrases = shuffledPhrases,
            pagerState = pagerState,
            onSelect = onSelect,
            onDeselect = onDeselect,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderGameContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onEvent: (OrderEvent) -> Unit,
    state: OrderState,
) {
    ShuffledPhrases(
        modifier = modifier,
        pagerState = pagerState,
        shuffledPhrases = state.shuffledPhrases,
        onSelect = { word ->
            onEvent(
                OrderEvent.OnAddWord(
                    phraseIndex = pagerState.currentPage,
                    word = word
                )
            )
        },
        onDeselect = { word ->
            onEvent(
                OrderEvent.OnRemoveWord(
                    phraseIndex = pagerState.currentPage,
                    word = word
                )
            )
        }
    )
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Siguiente",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun CheckButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Verificiar respuesta",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun FinishButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Finalizar",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun CorrectAnswer(
    modifier: Modifier = Modifier,
    phrase: String,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Frase correcta:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = phrase,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun Feedback(
    modifier: Modifier = Modifier,
    isCorrect: Boolean,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.errorContainer
            },
            contentColor = if (isCorrect) {
                MaterialTheme.colorScheme.onSecondaryContainer
            } else {
                MaterialTheme.colorScheme.onErrorContainer
            },
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                contentDescription = if (isCorrect) "Correct" else "Incorrect",
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = if (isCorrect) "¡Respuesta Correcta!" else "Respuesta Incorrecta",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    shuffledPhrase: ShuffledPhraseState?,
    isLast: Boolean,
    onCheck: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    when {
        isLast && shuffledPhrase?.isAnswered == true ->
            FinishButton(
                modifier = modifier,
                onClick = onFinish,
            )

        shuffledPhrase?.isAnswered == true ->
            NextButton(
                modifier = modifier,
                onClick = onNext,
            )


        else ->
            CheckButton(
                onClick = onCheck,
                enabled = shuffledPhrase?.selectedWords?.size == shuffledPhrase?.orderedWords?.size,
            )
    }
}

@Composable
fun OrderBottomBar(
    modifier: Modifier = Modifier,
    shuffledPhrase: ShuffledPhraseState?,
    isLast: Boolean,
    onFinish: () -> Unit,
    onCheck: () -> Unit,
    onNext: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if(shuffledPhrase?.isAnswered == true)
            Feedback(
                isCorrect = shuffledPhrase.isCorrect,
            )
        ActionButtons(
            modifier = modifier,
            shuffledPhrase = shuffledPhrase,
            isLast = isLast,
            onCheck = onCheck,
            onNext = onNext,
            onFinish = onFinish,
        )
    }
}

@Composable
fun OrderGameScreen(
    modifier: Modifier = Modifier,
    onEvent: (OrderEvent) -> Unit,
    state: OrderState,
) {
    var pagerState = rememberPagerState(pageCount = { state.shuffledPhrases.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            OrderBottomBar(
                shuffledPhrase = state.shuffledPhrases.getOrNull(pagerState.currentPage),
                onCheck = {
                    onEvent(OrderEvent.OnCheckClicked(pagerState.currentPage))
                },
                isLast = pagerState.currentPage == state.shuffledPhrases.size - 1,
                onFinish = {
                },
                onNext = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < pagerState.pageCount)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(nextPage)
                        }
                }
            )
        },
    ) { paddingValues ->
        OrderGameContent(
            modifier = Modifier.padding(paddingValues),
            pagerState = pagerState,
            state = state,
            onEvent = onEvent,
        )
    }
}