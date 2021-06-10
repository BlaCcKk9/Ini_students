package com.uni.inistudents.view

import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface EnterPinView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onFinish()
}