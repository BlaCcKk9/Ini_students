package com.uni.inistudents.presenter

import android.util.Log
import com.uni.inistudents.view.EnterPinView
import moxy.InjectViewState

@InjectViewState
class EnterPinPresenter: BasePresenter<EnterPinView>() {

    init {

    }

    fun onEnteredPin(pin: String){
        Log.e("strstr->>>", pin.toString())
        preferences().pin = pin
        viewState.onFinish()
    }
}