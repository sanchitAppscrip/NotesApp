package com.di

import android.content.Context
import com.room.NoteDao
import com.room.AppDatabase
import com.room.DatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return DatabaseBuilder(context).getInstance()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): NoteDao = appDatabase.noteDao()
}