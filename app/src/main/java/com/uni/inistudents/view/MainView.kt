package com.uni.inistudents.view

import com.uni.inistudents.model.DataInfo
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onOpenUniversityClicked()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setMainViewVisibility(university: DataInfo?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onEmptyAuth()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startWebViewScreen(token: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setEnterPinViewVisibility(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showWrongPinMessage()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun addAdView()
}