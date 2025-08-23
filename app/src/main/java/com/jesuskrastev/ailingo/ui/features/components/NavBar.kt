package com.jesuskrastev.ailingo.ui.features.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
) {
    @Immutable
    data class NavOption(
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val description: String? = null,
        val title: String,
        val onClick: () -> Unit
    )

    var selectedPage: Int by remember { mutableIntStateOf(0) }
    val listNavOptions: List<NavOption> = listOf(
        NavOption(
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            description = "Principal",
            title = "Principal",
            onClick = {

            },
        ),
        NavOption(
            selectedIcon = Icons.Filled.Language,
            unselectedIcon = Icons.Outlined.Language,
            description = "Webs",
            title = "Webs",
            onClick = {

            },
        ),
        NavOption(
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            description = "Perfil",
            title = "Perfil",
            onClick = {

            },
        ),
    )

    NavigationBar(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listNavOptions.forEachIndexed { index, button ->
                val selected = selectedPage == index

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if(selected) button.selectedIcon else button.unselectedIcon,
                            contentDescription = button.description,
                        )
                    },
                    label = {
                        Text(
                            text = button.title,
                        )
                    },
                    selected = selected,
                    onClick = {
                        if(!selected) button.onClick()
                        selectedPage = index
                    }
                )
            }
        }
    }
}