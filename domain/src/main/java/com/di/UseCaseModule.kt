package com.di

import com.usecases.UserUseCase
import com.usecases.UserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideUserUseCases(userUseCase: UserUseCaseImpl): UserUseCase = userUseCase
}