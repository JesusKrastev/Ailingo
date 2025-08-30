package com.jesuskrastev.ailingo.ui.features.games.match

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import kotlinx.coroutines.launch

@Composable
fun Option(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    isAnswered: Boolean,
    isMatched: Boolean,
    isCorrect: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                isAnswered && isCorrect -> MaterialTheme.colorScheme.primary
                isAnswered && !isCorrect -> MaterialTheme.colorScheme.error
                isMatched -> MaterialTheme.colorScheme.primary
                isSelected -> MaterialTheme.colorScheme.secondary
                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            contentColor = when {
                isAnswered && isCorrect -> MaterialTheme.colorScheme.onPrimary
                isAnswered && !isCorrect -> MaterialTheme.colorScheme.onError
                isMatched -> MaterialTheme.colorScheme.onPrimary
                isSelected -> MaterialTheme.colorScheme.onSecondary
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            },
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun Option(
    modifier: Modifier = Modifier,
    text: String,
    isMatched: Boolean,
    isAnswered: Boolean,
    isCorrect: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                isAnswered && isCorrect -> MaterialTheme.colorScheme.primary
                isAnswered && !isCorrect -> MaterialTheme.colorScheme.error
                isMatched -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            contentColor = when {
                isAnswered && isCorrect -> MaterialTheme.colorScheme.onPrimary
                isAnswered && !isCorrect -> MaterialTheme.colorScheme.onError
                isMatched -> MaterialTheme.colorScheme.onPrimary
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            },
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun Title(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "Combina las palabras",
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun OriginalOptions(
    modifier: Modifier = Modifier,
    words: List<WordState>,
    onSelect: (WordState) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        words.forEach { word ->
            Option(
                text = word.text,
                isSelected = word.isSelected,
                isAnswered = word.isAnswered,
                isCorrect = word.isCorrect,
                isMatched = word.isMatched,
                onClick = { onSelect(word) },
            )
        }
    }
}

@Composable
fun TranslationOptions(
    modifier: Modifier = Modifier,
    words: List<WordState>,
    onMatch: (WordState) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        words.forEach { word ->
            Option(
                text = word.text,
                isAnswered = word.isAnswered,
                isCorrect = word.isCorrect,
                isMatched = word.isMatched,
                onClick = { onMatch(word) },
            )
        }
    }
}

@Composable
fun WordsList(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    words: List<List<WordState>>,
    onSelectOption: (WordState) -> Unit,
    onMatchOption: (WordState) -> Unit,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        userScrollEnabled = false,
    ) { page ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OriginalOptions(
                modifier = Modifier.weight(0.5f),
                words = words[page].filter { !it.isTranslation },
                onSelect = onSelectOption,
            )
            TranslationOptions(
                modifier = Modifier.weight(0.5f),
                words = words[page].filter { it.isTranslation },
                onMatch = onMatchOption,
            )
        }
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
            text = "Pregunta $current de $total",
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
fun Words(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    words: List<List<WordState>>,
    onMatchOption: (WordState) -> Unit,
    onSelectOption: (WordState) -> Unit,
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
        WordsList(
            pagerState = pagerState,
            words = words,
            onSelectOption = onSelectOption,
            onMatchOption = onMatchOption,
        )
    }
}

@Composable
fun MatchGameContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    state: MatchState,
    onEvent: (MatchEvent) -> Unit,
) {
    Words(
        modifier = modifier,
        pagerState = pagerState,
        words = state.words,
        onMatchOption = { wordState ->
            onEvent(
                MatchEvent.OnMatchWord(
                    word = wordState,
                    pageIndex = pagerState.currentPage,
                )
            )
        },
        onSelectOption = { wordState ->
            onEvent(
                MatchEvent.OnSelectWord(
                    word = wordState,
                    pageIndex = pagerState.currentPage,
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
fun ActionButtons(
    modifier: Modifier = Modifier,
    words: List<WordState>?,
    isLast: Boolean,
    onCheck: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    when {
        isLast && words?.all { it.isAnswered } == true ->
            FinishButton(
                modifier = modifier,
                onClick = onFinish,
            )

        words?.all { it.isAnswered } == true ->
            NextButton(
                modifier = modifier,
                onClick = onNext,
            )


        else ->
            CheckButton(
                onClick = onCheck,
                enabled = words?.all { it.isMatched } == true,
            )
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
fun MatchBottomBar(
    modifier: Modifier = Modifier,
    words: List<WordState>?,
    isLast: Boolean,
    onFinish: () -> Unit,
    onCheck: () -> Unit,
    onNext: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (words?.all { it.isAnswered } == true)
            Feedback(
                isCorrect = words?.all { it.isCorrect } == true,
            )
        ActionButtons(
            words = words,
            onCheck = onCheck,
            onNext = onNext,
            onFinish = onFinish,
            isLast = isLast,
        )
    }
}

@Composable
fun MatchGameScreen(
    modifier: Modifier = Modifier,
    state: MatchState,
    onEvent: (MatchEvent) -> Unit,
) {
    var pagerState = rememberPagerState(pageCount = { state.words.size / 2 })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            MatchBottomBar(
                words = state.words.getOrNull(pagerState.currentPage),
                isLast = pagerState.currentPage == pagerState.pageCount - 1,
                onCheck = {
                    onEvent(MatchEvent.OnCheckClicked(pagerState.currentPage))
                },
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
        MatchGameContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent,
            pagerState = pagerState,
        )
    }
}