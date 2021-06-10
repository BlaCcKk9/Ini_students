package com.uni.inistudents

import android.content.SharedPreferences
import com.uni.inistudents.model.enum.Language

class Preferences(val preferences: SharedPreferences) {
    companion object {
        const val LANGUAGE = "language"
        const val TOKEN = "token"
        const val UNI_ID = "uni_id"
        const val UNI_TITLE = "uni_title"
        const val UNI_TITLE_EN = "uni_title_en"
        const val UNI_URL = "uni_url"
        const val UNI_PIN = "uni_pin"
    }

    var language
        get() = preferences.getString(LANGUAGE, Language.GEORGIAN.toString())
        set(value) = preferences.edit().putString(LANGUAGE, value).apply()

    var token
        get() = preferences.getString(TOKEN, "")
        set(value) = preferences.edit().putString(TOKEN, value).apply()

    var universityId
    get() = preferences.getString(UNI_ID, "")
    set(value) = preferences.edit().putString(UNI_ID, value).apply()

    var universityTitle
    get() = preferences.getString(UNI_TITLE, "")
    set(value) = preferences.edit().putString(UNI_TITLE, value).apply()

    var universityTitleEn
        get() = preferences.getString(UNI_TITLE_EN, "")
        set(value) = preferences.edit().putString(UNI_TITLE_EN, value).apply()

    var universityUrl
    get() = preferences.getString(UNI_URL, "")
    set(value) = preferences.edit().putString(UNI_URL, value).apply()

    var pin
    get() = preferences.getString(UNI_PIN, "")
    set(value) = preferences.edit().putString(UNI_PIN, value).apply()

}
