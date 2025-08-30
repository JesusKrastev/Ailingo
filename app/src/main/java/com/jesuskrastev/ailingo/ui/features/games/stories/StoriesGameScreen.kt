package com.jesuskrastev.ailingo.ui.features.games.stories

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jesuskrastev.ailingo.ui.features.games.components.ActionButton
import com.jesuskrastev.ailingo.ui.features.games.components.Feedback
import com.jesuskrastev.ailingo.ui.features.games.components.Loading
import kotlinx.coroutines.launch

@Composable
fun Option(
    modifier: Modifier = Modifier,
    text: String,
    isAnswered: Boolean,
    isCorrect: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                isAnswered && isCorrect -> MaterialTheme.colorScheme.primary
                isAnswered && isSelected && !isCorrect -> MaterialTheme.colorScheme.error
                isSelected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            contentColor = when {
                isAnswered && isCorrect -> MaterialTheme.colorScheme.onPrimary
                isAnswered && isSelected && !isCorrect -> MaterialTheme.colorScheme.onError
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
                isCorrect = story.correctOption == option,
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
    when {
        state.isLoading -> {
            Loading()
        }
        else -> {
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
            ActionButton(
                modifier = modifier,
                text = "Finalizar",
                onClick = onFinish,
            )

        story?.isAnswered == true ->
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
            if(!state.isLoading)
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