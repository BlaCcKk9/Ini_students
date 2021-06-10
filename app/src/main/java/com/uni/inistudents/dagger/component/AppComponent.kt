package com.uni.inistudents.dagger.component

import com.uni.inistudents.App
import com.uni.inistudents.dagger.module.AppModule
import com.uni.inistudents.dagger.module.StorageModule
import com.uni.inistudents.presenter.UtilityWrapper
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, StorageModule::class])
interface AppComponent {
    fun inject(utilityWrapper: UtilityWrapper)
    fun inject(app: App)
}