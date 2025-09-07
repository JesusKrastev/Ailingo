package com.jesuskrastev.ailingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.jesuskrastev.ailingo.ui.features.onboarding.language.LanguageScreen
import com.jesuskrastev.ailingo.ui.features.onboarding.level.LevelScreen
import com.jesuskrastev.ailingo.ui.navigation.AilingoNavHost
import com.jesuskrastev.ailingo.ui.theme.AilingoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            AilingoTheme {
                val navController = rememberNavController()

                Scaffold(
                    contentWindowInsets = WindowInsets.navigationBars,
                ) { paddingValues ->
                    AilingoNavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                    )
                }
            }
        }
    }
}