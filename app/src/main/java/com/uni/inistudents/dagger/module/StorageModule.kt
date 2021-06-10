package com.uni.inistudents.dagger.module

import android.content.Context
import android.preference.PreferenceManager
import com.uni.inistudents.Preferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun providePreferences() = Preferences(PreferenceManager.getDefaultSharedPreferences(applicationContext))
}