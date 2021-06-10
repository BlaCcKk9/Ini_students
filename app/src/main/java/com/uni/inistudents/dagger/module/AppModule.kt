package com.uni.inistudents.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {
    @Singleton
    @Provides
    fun provideApplicationContext() = applicationContext
}