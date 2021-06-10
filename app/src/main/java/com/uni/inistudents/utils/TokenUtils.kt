package com.uni.inistudents.utils

import com.uni.inistudents.Preferences

fun isGuest(preferences: Preferences) =
    preferences.token?.isEmpty()