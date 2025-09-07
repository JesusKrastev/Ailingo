package com.jesuskrastev.ailingo.ui.features.onboarding.method

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.VideogameAsset
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Subtitle
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Title

@Composable
fun Method(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun MethodScreen(
    modifier: Modifier = Modifier,
) {
    var methods by remember {
        mutableStateOf(
            listOf(
                MethodState(
                    title = "Juegos y actividades",
                    icon = Icons.Default.VideogameAsset,
                ),
                MethodState(
                    title = "Lectura",
                    icon = Icons.Default.MenuBook,
                ),
                MethodState(
                    title = "Flashcards",
                    icon = Icons.Default.Style,
                ),
                MethodState(
                    title = "Conversaciones con IA",
                    icon = Icons.Default.Assistant,
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
            text = "¿Cómo te gustaría aprender?",
        )
        Subtitle(
            text = "Elige tu estilo de aprendizaje preferido.",
        )
        methods.forEach { method ->
            Method(
                title = method.title,
                icon = method.icon,
            )
        }
    }
}