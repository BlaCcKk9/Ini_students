package com.uni.inistudents.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface BaseView : MvpView {
    fun toastShort(msg: String)
    fun toastShort(resId: Int)
    fun toastLong(msg: String)
    fun toastLong(resId: Int)
    fun showNoInternetConnectionError()
}