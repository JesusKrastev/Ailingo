package com.jesuskrastev.ailingo.ui.features.games.match

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Option(
    modifier: Modifier = Modifier,
    text: String,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = { /*TODO*/ }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun MatchGameContent(
    modifier: Modifier = Modifier,
) {
    val animatedProgress = animateFloatAsState(
        targetValue = 0.75f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            progress = animatedProgress,
        )
        Text(
            text = "Which word is most related to 'happy'?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
        )
        Option(
            text = "joyful",
        )
        Option(
            text = "sad",
        )
        Option(
            text = "angry",
        )
        Option(
            text = "tired",
        )
    }
}

@Composable
fun MatchGameScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        MatchGameContent(
            modifier = Modifier.padding(paddingValues),
        )
    }
}