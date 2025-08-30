package com.jesuskrastev.ailingo.ui.features.games.phrases

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jesuskrastev.ailingo.ui.composables.shimmerEffect
import com.jesuskrastev.ailingo.ui.features.games.components.ActionButton
import com.jesuskrastev.ailingo.ui.features.games.components.Feedback
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IncompletePhrase(
    modifier: Modifier = Modifier,
    prefix: String,
    suffix: String,
    missingWord: String,
) {
    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val prefixMiddle = prefix.length / 2
        val prefixPart1 = prefix.substring(0, prefixMiddle)
        val prefixPart2 = prefix.substring(prefixMiddle)
        val suffixMiddle = suffix.length / 2
        val suffixPart1 = suffix.substring(0, suffixMiddle)
        val suffixPart2 = suffix.substring(suffixMiddle)

        Text(
            text = prefixPart1,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = prefixPart2,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
        )
        Box(
            modifier = Modifier
                .size(width = (missingWord.length * 12.dp.value).dp, height = 2.dp)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.Bottom)
        )
        Text(
            text = suffixPart1,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = suffixPart2,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
fun Option(
    modifier: Modifier = Modifier,
    text: String,
    isAnswered: Boolean,
    isCorrectAnswer: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                isAnswered && isCorrectAnswer -> MaterialTheme.colorScheme.primary
                isAnswered && isSelected && !isCorrectAnswer -> MaterialTheme.colorScheme.error
                isSelected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            contentColor = when {
                isAnswered && isCorrectAnswer -> MaterialTheme.colorScheme.onPrimary
                isAnswered && isSelected && !isCorrectAnswer -> MaterialTheme.colorScheme.onError
                isSelected -> MaterialTheme.colorScheme.onPrimary
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            },
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
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
fun Title(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "Completa la frase:",
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun Phrase(
    modifier: Modifier = Modifier,
    phrase: PhraseState,
    onSelectOption: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IncompletePhrase(
            prefix = phrase.prefix,
            suffix = phrase.suffix,
            missingWord = phrase.missingWord,
        )
        phrase.options.forEach { option ->
            Option(
                modifier = modifier,
                text = option,
                isSelected = phrase.selectedOption == option,
                isAnswered = phrase.isAnswered,
                isCorrectAnswer = phrase.missingWord == option,
                onClick = {
                    if (!phrase.isAnswered) onSelectOption(option)
                }
            )
        }
    }
}

@Composable
fun PhrasesList(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    phrases: List<PhraseState>,
    onSelectOption: (String) -> Unit,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        userScrollEnabled = false,
    ) {
        Phrase(
            phrase = phrases[it],
            onSelectOption = onSelectOption,
        )
    }
}

@Composable
fun Phrases(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    phrases: List<PhraseState>,
    onSelectOption: (String) -> Unit,
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
        PhrasesList(
            modifier = Modifier.fillMaxWidth(),
            pagerState = pagerState,
            phrases = phrases,
            onSelectOption = onSelectOption,
        )
    }
}

@Composable
fun IncompletePhraseLoader(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.shimmerEffect(),
        color = Color.Transparent,
        text = "Example phrase",
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun ProgressBarLoader(
    modifier: Modifier = Modifier,
) {
    LinearProgressIndicator(
        progress = 0f,
        modifier = modifier
            .fillMaxWidth()
            .shimmerEffect()
            .height(10.dp)
    )
}

@Composable
fun TitleLoader(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.shimmerEffect(),
        text = "Completa la frase:",
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleLarge,
        color = Color.Transparent,
    )
}

@Composable
fun OptionLoader(
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .shimmerEffect(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = {},
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Example option",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun PhrasesLoader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProgressBarLoader()
        TitleLoader()
        IncompletePhraseLoader()
        repeat(4) {
            OptionLoader()
        }
    }
}

@Composable
fun CompleteGameContent(
    modifier: Modifier = Modifier,
    state: CompleteState,
    pagerState: PagerState,
    onEvent: (CompleteEvent) -> Unit,
) {
    when {
        state.phrases.isEmpty() -> {
            PhrasesLoader(
                modifier = modifier,
            )
        }

        else -> {
            Phrases(
                modifier = modifier,
                pagerState = pagerState,
                phrases = state.phrases,
                onSelectOption = { option ->
                    onEvent(
                        CompleteEvent.OnOptionSelected(
                            option = option,
                            phraseIndex = pagerState.currentPage,
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    phrase: PhraseState?,
    isLast: Boolean,
    onCheck: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    when {
        isLast && phrase?.isAnswered == true ->
            ActionButton(
                modifier = modifier,
                text = "Finalizar",
                onClick = onFinish,
            )

        phrase?.isAnswered == true ->
            ActionButton(
                modifier = modifier,
                text = "Siguiente",
                onClick = onNext,
            )


        else ->
            ActionButton(
                modifier = modifier,
                text = "Comprobar respuesta",
                onClick = onCheck,
                enabled = phrase?.selectedOption?.isNotEmpty() == true,
            )
    }
}

@Composable
fun PhrasesBottomBar(
    modifier: Modifier = Modifier,
    phrase: PhraseState?,
    isLast: Boolean,
    onFinish: () -> Unit,
    onCheck: () -> Unit,
    onNext: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (phrase?.isAnswered == true)
            Feedback(
                isCorrect = phrase?.selectedOption == phrase?.missingWord,
            )
        ActionButtons(
            phrase = phrase,
            onCheck = onCheck,
            onNext = onNext,
            onFinish = onFinish,
            isLast = isLast,
        )
    }
}

@Composable
fun CompleteGameScreen(
    modifier: Modifier = Modifier,
    state: CompleteState,
    onEvent: (CompleteEvent) -> Unit,
) {
    var pagerState = rememberPagerState(pageCount = { state.phrases.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            PhrasesBottomBar(
                phrase = state.phrases.getOrNull(pagerState.currentPage),
                onCheck = {
                    onEvent(CompleteEvent.OnCheckClicked(pagerState.currentPage))
                },
                isLast = pagerState.currentPage == state.phrases.size - 1,
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
        CompleteGameContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent,
            pagerState = pagerState,
        )
    }
}