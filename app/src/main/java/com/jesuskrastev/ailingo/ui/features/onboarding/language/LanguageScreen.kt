package com.jesuskrastev.ailingo.ui.features.onboarding.language

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Subtitle
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Title
import com.jesuskrastev.ailingo.ui.navigation.Destination
import kotlinx.serialization.Serializable

@Composable
fun Language(
    modifier: Modifier = Modifier,
    image: Uri,
    name: String,
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape),
                model = image,
                contentDescription = "Language",
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = name,
            )
        }
    }
}

@Composable
fun LanguageScreen(
    modifier: Modifier = Modifier,
) {
    var languages by remember {
        mutableStateOf(
            listOf(
                LanguageState(
                    name = "English",
                    image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZfy-zUsOsWD7jNVy-e6oY_x60Y6075waPAw&s".toUri(),
                ),
                LanguageState(
                    name = "Español",
                    image = "https://m.media-amazon.com/images/I/61-7ZqMkUjL._UF894,1000_QL80_.jpg".toUri(),
                ),
                LanguageState(
                    name = "Français",
                    image = "https://img.freepik.com/fotos-premium/bandera-francia-bandera-francesa-superficie-tela-pais-europeo_929087-12163.jpg".toUri(),
                ),
                LanguageState(
                    name = "Русский",
                    image = "https://img.freepik.com/fotos-premium/bandera-federacion-rusa-bandera-rusa-superficie-tela-simbolo-nacional_929087-12213.jpg".toUri(),
                ),
                LanguageState(
                    name = "Português",
                    image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpghI2ml0rh25fyxhe4o6s-oaX1B7ypCD_LQ&s".toUri(),
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
            text = "¿Qué idioma quieres aprender?",
        )
        Subtitle(
            text = "Selecciona un idioma para comenzar tu aventura.",
        )
        languages.forEach { language ->
            Language(
                name = language.name,
                image = language.image,
            )
        }
    }
}