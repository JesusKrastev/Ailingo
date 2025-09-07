package com.jesuskrastev.ailingo.ui.features.onboarding.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.SouthAmerica
import androidx.compose.material.icons.filled.Work
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
import androidx.compose.ui.unit.dp
import com.jesuskrastev.ailingo.ui.composables.NonlazyGrid
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Subtitle
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Title

@Composable
fun Goal(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun GoalScreen(
    modifier: Modifier = Modifier,
) {
    var goals by remember {
        mutableStateOf(
            listOf(
                GoalState(
                    title = "Hablar",
                    icon = Icons.Default.Mic,
                ),
                GoalState(
                    title = "Leer",
                    icon = Icons.Default.Book,
                ),
                GoalState(
                    title = "Escribir",
                    icon = Icons.Default.Edit,
                ),
                GoalState(
                    title = "Viajar",
                    icon = Icons.Default.AirplanemodeActive,
                ),
                GoalState(
                    title = "Trabajo",
                    icon = Icons.Default.Work,
                ),
                GoalState(
                    title = "Cultura",
                    icon = Icons.Filled.SouthAmerica,
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
            text = "¿Cuál es tu objetivo principal?",
        )
        Subtitle(
            text = "Selecciona el objetivo que mejor se adapte a tus necesidades.",
        )
        NonlazyGrid(
            columns = 2,
            itemCount = goals.size,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val goal = goals[it]

            Goal(
                modifier = Modifier.fillMaxWidth(),
                icon = goal.icon,
                title = goal.title,
            )
        }
    }
}