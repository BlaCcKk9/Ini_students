package com.uni.inistudents.utils

import android.content.Context
import android.os.Build
import com.uni.inistudents.model.enum.Language
import java.util.*

object LocaleHelper {
    fun setLanguage(context: Context, language: Language) {
        setLanguage(context, language.toString())
    }

    private fun setLanguage(context: Context, language: String) {
        Language.clearSelection()
        Language.get(language).isSelected = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createConfigurationContext(context, language)
        } else {
            updateResourcesLegacy(context, language)
        }
    }

    private fun createConfigurationContext(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        configuration.fontScale = 1f
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String) {
        val locale = Locale(language, Language.get(language).country)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        configuration.fontScale = 1f
//        return context.createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
