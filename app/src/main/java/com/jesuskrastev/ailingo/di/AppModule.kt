package com.jesuskrastev.ailingo.di

import android.content.Context
import com.jesuskrastev.ailingo.data.room.AilingoDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AilingoDB = AilingoDB.getDatabase(context)

    @Singleton
    @Provides
    fun provideTermDao(
        db: AilingoDB
    ) = db.termDao()
}