package com.jesuskrastev.ailingo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AilingoApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}