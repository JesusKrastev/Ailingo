package com.jesuskrastev.ailingo.ui.features.games

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jesuskrastev.ailingo.ui.composables.NonlazyGrid
import com.jesuskrastev.ailingo.ui.features.games.components.Game

@Composable
fun GameList(
    modifier: Modifier = Modifier,
) {
    @Immutable
    data class GameItem(
        val icon: ImageVector,
        val title: String,
        val difficulty: String,
        val color: Color,
    )
    val games = listOf(
        GameItem(
            icon = Icons.Default.ChangeCircle,
            title = "Empareja Palabras",
            difficulty = "Fácil",
            color = Color(0xFF2ED573),
        ),
        GameItem(
            icon = Icons.Default.SpaceBar,
            title = "Completa Frases",
            difficulty = "Intermedio",
            color = Color(0xFFFF9F43),
        ),
        GameItem(
            icon = Icons.Default.Reorder,
            title = "Ordena\nFrases",
            difficulty = "Intermdio",
            color = Color(0xFF1E90FF),
        ),
        GameItem(
            icon = Icons.Default.QuestionMark,
            title = "Responde Preguntas",
            difficulty = "Difícil   ",
            color = Color(0xFFFF4757),
        ),
    )

    NonlazyGrid(
        modifier = modifier,
        columns = 2,
        itemCount = games.size,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val game = games[it]

        Game(
            difficulty = game.difficulty,
            title = game.title,
            color = game.color,
            icon = game.icon,
        )
    }
}

@Composable
fun GamesContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GameList()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Juegos")
                },
            )
        },
    ) { paddingValues ->
        GamesContent(
            modifier = Modifier.padding(paddingValues),
        )
    }
}