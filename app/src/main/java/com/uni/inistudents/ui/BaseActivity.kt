package com.uni.inistudents.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import com.uni.inistudents.R
import com.uni.inistudents.presenter.BasePresenter
import com.uni.inistudents.view.BaseView
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatActivity
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.util.*

abstract class BaseActivity: MvpAppCompatActivity(), BaseView {

    abstract val presenter: BasePresenter<out BaseView>

    abstract fun getLayoutResID(): Int

    abstract fun setupView(savedInstanceState: Bundle?)

//    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(newBase)
//        val config = Configuration()
//        applyOverrideConfiguration(config)
//    }
//
//    override fun applyOverrideConfiguration(newConfig: Configuration) {
//        super.applyOverrideConfiguration(updateConfigurationIfSupported(newConfig))
//    }
//
//    open fun updateConfigurationIfSupported(config: Configuration): Configuration? {
//        if (Build.VERSION.SDK_INT >= 24) {
//            if (!config.locales.isEmpty) {
//                return config
//            }
//        } else {
//            if (config.locale != null) {
//                return config
//            }
//        }
//        val locale = Locale(Language.selectedLanguage().toString())
//        config.setLocale(locale)
//        return config
//    }

    private val noInternetConnectionSnackbar by lazy {
        Snackbar.make(
            window.decorView.findViewById(android.R.id.content),
            R.string.msg_error_no_internet_connection, Snackbar.LENGTH_LONG
        )
            .setAction(R.string.settings) {
                showInternetSettingsScreen()
            }
    }

//    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(newBase)
//        val config = Configuration()
//        applyOverrideConfiguration(config)
//    }
//
//    override fun applyOverrideConfiguration(newConfig: Configuration) {
//        super.applyOverrideConfiguration(updateConfigurationIfSupported(newConfig))
//    }
//
//    open fun updateConfigurationIfSupported(config: Configuration): Configuration? {
//        if (Build.VERSION.SDK_INT >= 24) {
//            if (!config.locales.isEmpty) {
//                return config
//            }
//        } else {
//            if (config.locale != null) {
//                return config
//            }
//        }
//        val locale = Locale(Language.selectedLanguage().toString())
//        config.setLocale(locale)
//        return config
//    }


    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        loadLanguage()
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResID())
        setupView(savedInstanceState)
    }

    override fun toastShort(msg: String) {
        toast(msg)
    }

    override fun toastShort(resId: Int) {
        toast(resId)
    }

    override fun toastLong(msg: String) {
        longToast(msg)
    }

    override fun toastLong(resId: Int) {
        longToast(resId)
    }

    override fun showNoInternetConnectionError() {
        if (!noInternetConnectionSnackbar.isShown)
            noInternetConnectionSnackbar.show()
    }


    private fun showInternetSettingsScreen() {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
    private fun loadLanguage() {
        val locale = Locale("ka")
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}