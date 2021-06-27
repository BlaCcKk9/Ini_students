package com.uni.inistudents.view

import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface WebViewView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startWebView(token: String, url: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setEnterPinVisibility(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onCancelPinClicked()
}