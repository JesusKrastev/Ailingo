package com.jesuskrastev.ailingo.ui.features.onboarding.survey

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonChecked
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Subtitle
import com.jesuskrastev.ailingo.ui.features.onboarding.components.Title

@Composable
fun SelectedOption(
    modifier: Modifier = Modifier,
    text: String,
) {
    Card(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                fontSize = 19.sp,
            )
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Check",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun UnselectedOption(
    modifier: Modifier = Modifier,
    text: String,
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
            Text(
                text = text,
                fontSize = 19.sp,
            )
            Icon(
                imageVector = Icons.Default.RadioButtonUnchecked,
                contentDescription = "Radio Button",
            )
        }
    }
}

@Composable
fun Option(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
) {
    if (isSelected) {
        SelectedOption(
            modifier = modifier,
            text = text,
        )
    } else {
        UnselectedOption(
            modifier = modifier,
            text = text,
        )
    }
}

@Composable
fun SurveyScreen(
    modifier: Modifier = Modifier,
) {
    var options by remember {
        mutableStateOf(
            listOf(
                OptionState(
                    text = "Usando esta app",
                    isSelected = true,
                ),
                OptionState(
                    text = "Estudiando en la escuela",
                ),
                OptionState(
                    text = "Con Duolingo",
                ),
                OptionState(
                    text = "En una academia",
                ),
                OptionState(
                    text = "Viendo series y películas",
                ),
                OptionState(
                    text = "Escuchando música o podcasts",
                ),
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(
            text = "Estudia de la forma correcta",
        )
        Subtitle(
            text = "¿Y tú, como estás aprendiendo?"
        )
        options.forEach { option ->
            Option(
                text = option.text,
                isSelected = option.isSelected
            )
        }
    }
}