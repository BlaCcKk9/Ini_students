package com.uni.inistudents.presenter

import android.content.Context
import com.uni.inistudents.App
import com.uni.inistudents.Preferences
import com.uni.inistudents.network.RestApi
import javax.inject.Inject

class UtilityWrapper {
    @Inject
    lateinit var applicationContext: Context

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var restApi: RestApi

    init {
        App.appComponent.inject(this)
    }
}