package com.zainco.androidcookiescompose.gyms.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier//helps us provide another different dispatcher from the Dispatchers.Main, if needed
@Retention(AnnotationRetention.BINARY)//just determines how our annotation is stored
annotation class MainDispatcher


@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}