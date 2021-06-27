package com.uni.inistudents

import android.app.Application
import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.uni.inistudents.dagger.component.AppComponent
import com.uni.inistudents.dagger.component.DaggerAppComponent
import com.uni.inistudents.dagger.module.AppModule
import com.uni.inistudents.dagger.module.StorageModule
import javax.inject.Inject

class App : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var appComponent: AppComponent
        private var appOpenManager: AppOpenManager? = null
    }

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate() {
        super.onCreate()
        appContext = this
        configureDagger()
        initializeMobileAds()
    }

    private fun configureDagger(){
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .storageModule(StorageModule(this))
            .build()
        App.appComponent.inject(this)
    }

    private fun initializeMobileAds(){
        MobileAds.initialize(this) { }
        appOpenManager = AppOpenManager(this)
    }

}