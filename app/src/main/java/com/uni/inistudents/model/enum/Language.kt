package com.uni.inistudents.model.enum

import androidx.annotation.StringRes
import com.uni.inistudents.App
import com.uni.inistudents.R

enum class Language {
    GEORGIAN{
//        override val title = getString(R.string.language_georgian)
        override fun toString() = "ka"
        override val country = "GE"
        override val serverValue = "GE"
        override var isSelected = false
        override val flag: Int = R.drawable.ic_georgia
    },

    ENGLISH{
//        override val title = getString(R.string.language_english)
        override fun toString() = "en"
        override val country = "US"
        override val serverValue = "EN"
        override var isSelected = false
        override val flag: Int = R.drawable.ic_united_kingdom
    };

//    abstract val title: String
    abstract val country: String
    abstract val serverValue: String
    abstract var isSelected: Boolean
    abstract val flag: Int

    protected fun getString(@StringRes resId: Int) =
            App.appContext.getString(resId).orEmpty()

    companion object {
        fun get(position: Int) = Language.values()[position]
        fun get(language: String) =
                when (language) {
                    GEORGIAN.toString() -> GEORGIAN
                    ENGLISH.toString() -> ENGLISH
                    else -> GEORGIAN
                }

        fun selectedLanguage() = values().find { it.isSelected } ?: GEORGIAN

        fun clearSelection() {
            values().forEach { it.isSelected = false }
        }
    }
}