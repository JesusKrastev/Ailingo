package com.jesuskrastev.ailingo.ui.features.onboarding.benefits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import kotlin.math.pow

@Composable
fun Chart(
    modifier: Modifier = Modifier,
) {
    LineChart(
        modifier = modifier.height(200.dp),
        labelHelperProperties = LabelHelperProperties(
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        ),
        gridProperties = GridProperties(
            enabled = false,
        ),
        indicatorProperties = HorizontalIndicatorProperties(
            enabled = false,
        ),
        dividerProperties = DividerProperties(
            enabled = false,
        ),
        data = remember {
            listOf(
                Line(
                    label = "Tradicional",
                    values = (0..300).map { 0.005 * it.toDouble().pow(2) + it * 0.05 },
                    color = SolidColor(Color(0xFFE91E63)),
                    firstGradientFillColor = Color(0xFFE91E63).copy(alpha = 0.5f),
                    secondGradientFillColor = Color.Transparent,
                    drawStyle = DrawStyle.Stroke(width = 3.dp),
                ),
                Line(
                    label = "Con app",
                    values = (0..500).map { 0.005 * it.toDouble().pow(2) + it * 0.05 },
                    color = SolidColor(Color(0xFF1EA9E9)),
                    drawStyle = DrawStyle.Stroke(width = 3.dp),
                    firstGradientFillColor = Color(0xFF1EA9E9).copy(alpha = 0.2f),
                    secondGradientFillColor = Color.Transparent,
                )
            )
        },
        labelProperties = LabelProperties(
            enabled = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        ),
        animationMode = AnimationMode.Together(
            delayBuilder = {
                it * 500L
            }
        ),
    )
}

@Composable
fun LearningProgress(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            LearningTitle()
            LearningSubtitle()
            Chart()
            TimeLabels()
        }
    }
}

@Composable
fun LearningTitle(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "Progreso de aprendizaje en 6 meses",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun LearningSubtitle(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "+35%",
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold,
    )
}

@Composable
fun TimeLabels(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        (0..4).map { "${it * 3}m" }.forEach { label ->
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}

@Composable
fun BenefitsScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Tu progreso comparado con otros métodos",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
        )
        LearningProgress()
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Celebration,
                contentDescription = "Celebration",
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Nuestros usuarios muestran un progreso significativamente mayor en comparación con los métodos tradicionales. ¡Sigue así!",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}