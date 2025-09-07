package com.jesuskrastev.ailingo.ui.features.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jesuskrastev.ailingo.ui.features.onboarding.benefits.BenefitsScreen
import com.jesuskrastev.ailingo.ui.features.onboarding.goal.GoalScreen
import com.jesuskrastev.ailingo.ui.features.onboarding.language.LanguageScreen
import com.jesuskrastev.ailingo.ui.features.onboarding.level.LevelScreen
import com.jesuskrastev.ailingo.ui.features.onboarding.method.MethodScreen
import com.jesuskrastev.ailingo.ui.features.onboarding.survey.SurveyScreen
import com.jesuskrastev.ailingo.ui.features.onboarding.time.TimeScreen
import kotlinx.coroutines.launch

@Composable
fun OnboardingContent(
    modifier: Modifier = Modifier,
) {
    var pages: List<@Composable () -> Unit> = listOf(
        { LanguageScreen() },
        { LevelScreen() },
        { GoalScreen() },
        { BenefitsScreen() },
        { TimeScreen() },
        { MethodScreen() },
        { SurveyScreen() },
    )
    var pagerState = rememberPagerState(pageCount = { pages.size })
    var coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.clickable(
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.currentPage > 0)
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                ),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                progress = (pagerState.currentPage + 1) / pages.size.toFloat(),
            )
        }
        HorizontalPager(
            modifier = Modifier.weight(1f),
            pageSpacing = 16.dp,
            state = pagerState,
            userScrollEnabled = false,
        ) { index ->
            pages[index]()
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                if(pagerState.currentPage < pages.size - 1)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                text = if (pagerState.currentPage == pages.size - 1) "Empezar a aprender bien" else "Continuar",
            )
        }
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        OnboardingContent(
            modifier = Modifier.padding(paddingValues),
        )
    }
}