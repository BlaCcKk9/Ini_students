package com.uni.inistudents.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.uni.inistudents.R

fun dialogFragmentAddServiceCategoryLayoutParams(dialog: Dialog, context: Context) {
    val params = dialog.window!!.attributes
    params.x = 0
    params.y = 0
    val displaySize = getDisplayDimensions(context)
    val width = displaySize.x - 0 - 0
    val height = displaySize.y - 0 - 0
    dialog.window!!.apply {
        setLayout(width, ViewGroup.LayoutParams.MATCH_PARENT)
        setWindowAnimations(R.style.DialogAnimation)
        setBackgroundDrawableResource(R.drawable.bg_dialog_rounded)
        setGravity(Gravity.BOTTOM)
        attributes = params
    }
}

private fun getDisplayDimensions(context: Context): Point {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay

    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    val screenWidth = metrics.widthPixels
    var screenHeight = metrics.heightPixels

    // find out if status bar has already been subtracted from screenHeight
    display.getRealMetrics(metrics)
    val physicalHeight = metrics.heightPixels
    val statusBarHeight = getStatusBarHeight(context)
    val navigationBarHeight = getNavigationBarHeight(context)
    val heightDelta = physicalHeight - screenHeight
    if (heightDelta == 0 || heightDelta == navigationBarHeight) {
        screenHeight -= statusBarHeight
    }

    return Point(screenWidth, screenHeight)
}



private fun getStatusBarHeight(context: Context): Int {
    val resources = context.resources
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}


private fun getNavigationBarHeight(context: Context): Int {
    val resources = context.resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}