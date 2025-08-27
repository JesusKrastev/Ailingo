package com.jesuskrastev.ailingo.ui.features.games.stories

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jesuskrastev.ailingo.ui.features.games.phrases.PhrasesEvent
import kotlinx.coroutines.launch

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if(isSelected)
                Icon(
                    imageVector = Icons.Default.RadioButtonChecked,
                    contentDescription = "Radio Button",
                )
            else
                Icon(
                    imageVector = Icons.Default.RadioButtonUnchecked,
                    contentDescription = "Radio Button",
                )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun Story(
    modifier: Modifier = Modifier,
    story: StoryState,
    onSelectOption: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = story.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = story.text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = story.question,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
        )
        story.options.forEach { option ->
            Option(
                text = option,
                isSelected = story.selectedOption == option,
                isAnswered = story.isAnswered,
                isCorrectAnswer = story.correctOption == option,
                onClick = {
                    if (!story.isAnswered) onSelectOption(option)
                }
            )
        }
    }
}

@Composable
fun StoryList(
    modifier: Modifier = Modifier,
    stories: List<StoryState>,
    onSelectOption: (String) -> Unit,
    pagerState: PagerState,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        userScrollEnabled = false,
    ) {
        Story(
            story = stories[it],
            onSelectOption = onSelectOption,
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
fun Stories(
    modifier: Modifier = Modifier,
    stories: List<StoryState>,
    onSelectOption: (String) -> Unit,
    pagerState: PagerState,
) {
    val animatedProgress = animateFloatAsState(
        targetValue = 0.75f,
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
        StoryList(
            stories = stories,
            pagerState = pagerState,
            onSelectOption = onSelectOption,
        )
    }
}

@Composable
fun HistoryGameContent(
    modifier: Modifier = Modifier,
    state: StoriesState,
    pagerState: PagerState,
    onEvent: (StoriesEvent) -> Unit,
) {
    Stories(
        modifier = modifier,
        stories = state.stories,
        pagerState = pagerState,
        onSelectOption = { option ->
            onEvent(
                StoriesEvent.OnOptionSelected(
                    storyIndex = pagerState.currentPage,
                    option = option,
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
    story: StoryState?,
    isLast: Boolean,
    onCheck: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    when {
        isLast && story?.isAnswered == true ->
            FinishButton(
                modifier = modifier,
                onClick = onFinish,
            )

        story?.isAnswered == true ->
            NextButton(
                modifier = modifier,
                onClick = onNext,
            )


        else ->
            CheckButton(
                onClick = onCheck,
                enabled = story?.selectedOption?.isNotEmpty() == true,
            )
    }
}

@Composable
fun StoriesBottomBar(
    modifier: Modifier = Modifier,
    story: StoryState?,
    isLast: Boolean,
    onFinish: () -> Unit,
    onCheck: () -> Unit,
    onNext: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (story?.isAnswered == true)
            Feedback(
                isCorrect = story?.isCorrect == true,
            )
        ActionButtons(
            story = story,
            onCheck = onCheck,
            onNext = onNext,
            onFinish = onFinish,
            isLast = isLast,
        )
    }
}

@Composable
fun HistoryGameScreen(
    modifier: Modifier = Modifier,
    state: StoriesState,
    onEvent: (StoriesEvent) -> Unit = {},
) {
    var pagerState = rememberPagerState(pageCount = { state.stories.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            StoriesBottomBar(
                story = state.stories.getOrNull(pagerState.currentPage),
                onCheck = {
                    onEvent(StoriesEvent.OnCheckClicked(pagerState.currentPage))
                },
                isLast = pagerState.currentPage == state.stories.size - 1,
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
        HistoryGameContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            pagerState = pagerState,
            onEvent = onEvent,
        )
    }
}