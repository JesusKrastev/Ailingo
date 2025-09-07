package com.jesuskrastev.ailingo.ui.features.onboarding.level

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Subtitle
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Title

@Composable
fun Level(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Icon(
                imageVector = Icons.Default.RadioButtonUnchecked,
                contentDescription = "Radio Button",
            )
        }
    }
}

@Composable
fun LevelScreen(
    modifier: Modifier = Modifier,
) {
    var levels by remember {
        mutableStateOf(
            listOf(
                LevelState(
                    title = "Principiante",
                    description = "Estoy empezando desde cero.",
                ),
                LevelState(
                    title = "Intermedio",
                    description = "Ya tengo experiencia con el idioma.",
                ),
                LevelState(
                    title = "Avanzado",
                    description = "Tengo bastante fluidez.",
                ),
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Title(
            modifier = Modifier,
            text = "¿Cuál es tu nivel de idioma?",
        )
        Subtitle(
            text = "Esto nos ayudará a personalizar tu experiencia de aprendizaje.",
        )
        levels.forEach { level ->
            Level(
                title = level.title,
                description = level.description,
            )
        }
    }
}