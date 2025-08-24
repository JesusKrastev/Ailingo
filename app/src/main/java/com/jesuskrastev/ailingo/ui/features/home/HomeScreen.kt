package com.jesuskrastev.ailingo.ui.features.home


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jesuskrastev.ailingo.ui.composables.NonlazyGrid
import com.jesuskrastev.ailingo.ui.composables.shimmerEffect

@Composable
fun TermOfTheDay(
    modifier: Modifier = Modifier,
    term: TermState,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionTitle(
            title = "Palabra del día"
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Column {
                    WordTitle(word = term.term)
                    WordSubtitle(word = term.translation)
                }
                WordDescription(
                    description = term.definition,
                )
            }
        }
    }
}

@Composable
fun WordTitleLoader(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.shimmerEffect(),
        text = "Example",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = Color.Transparent,
    )
}

@Composable
fun WordSubtitleLoader(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.shimmerEffect(),
        text = "Ejemplo",
        color = Color.Transparent,
    )
}

@Composable
fun WordDescriptionLoader(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.shimmerEffect(),
        text = "This is an example description. It can be longer than the title.",
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Transparent,
    )
}

@Composable
fun TermOfTheDayLoader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionTitle(
            title = "Palabra del día"
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Column {
                    WordTitleLoader()
                    WordSubtitleLoader()
                }
                WordDescriptionLoader()
            }
        }
    }
}

@Composable
fun WordTitle(
    modifier: Modifier = Modifier,
    word: String,
) {
    Text(
        modifier = modifier,
        text = word,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
fun WordSubtitle(
    modifier: Modifier = Modifier,
    word: String,
) {
    Text(
        modifier = modifier,
        text = word,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun WordDescription(
    modifier: Modifier = Modifier,
    description: String,
) {
    Text(
        modifier = modifier,
        text = description,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
fun PracticeButton(
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        onClick = { /*TODO*/ }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.PlayCircle,
                contentDescription = "Practicar",
            )
            Text(
                text = "Practicar ahora",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun DailyProgress(
    modifier: Modifier = Modifier,
) {
    val animatedProgress = animateFloatAsState(
        targetValue = 0.75f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Progreso diario",
                    fontWeight = FontWeight.SemiBold,
                )
                Row {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Racha",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = "23 Días",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                progress = animatedProgress,
            )
        }
    }
}

@Composable
fun QuickAccess(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = text,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
fun QuickAccessList(
    modifier: Modifier = Modifier,
) {
    @Immutable
    data class Activity(
        val icon: ImageVector,
        val text: String,
        val onClick: () -> Unit = {},
    )
    val activities = listOf(
        Activity(
            icon = Icons.Default.Edit,
            text = "Ejercicios",
        ),
        Activity(
            icon = Icons.Default.TextFields,
            text = "Vocabulario",
        ),
        Activity(
            icon = Icons.Default.Mic,
            text = "Pronunciación",
        ),
        Activity(
            icon = Icons.Default.Book,
            text = "Historias",
        ),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionTitle(
            title = "Accesos rápidos"
        )
        NonlazyGrid(
            columns = 2,
            itemCount = activities.size,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val activity = activities[it]

            QuickAccess(
                modifier = Modifier.fillMaxWidth(),
                icon = activity.icon,
                text = activity.text,
            )
        }
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        modifier = modifier,
        text = title,
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
                model = "https://i.pinimg.com/736x/d9/d8/8e/d9d88e3d1f74e2b8ced3df051cecb81d.jpg",
            contentDescription = "Perfil",
            contentScale = ContentScale.Crop,
        )
        Column {
            Text(
                text = "¡Hola, Jesús!",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "Sigamos aprendiendo",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeState,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Greetings()
        }
        item {
            DailyProgress()
        }
        item {
            PracticeButton()
        }
        item {
            if(!state.isLoading)
                TermOfTheDay(
                    term = state.term,
                )
            else
                TermOfTheDayLoader()
        }
        item {
            QuickAccessList()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.statusBars,
    ) { paddingValues ->
        HomeContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
        )
    }
}