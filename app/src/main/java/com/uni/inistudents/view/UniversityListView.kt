package com.uni.inistudents.view

import com.uni.inistudents.adapter.UniversityListAdapter
import com.uni.inistudents.model.DataInfo
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface UniversityListView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onCloseDialogClicked(university: DataInfo?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setUniversityAdapter(adapter: UniversityListAdapter)
}