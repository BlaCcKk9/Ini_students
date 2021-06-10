package com.uni.inistudents.presenter

import android.util.Log
import com.uni.inistudents.model.FirebaseTokenBody
import com.uni.inistudents.view.WebViewView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState

@InjectViewState
class WebViewPresenter(private var token: String): BasePresenter<WebViewView>() {

    init {
        if (preferences().pin!!.isEmpty()) viewState.setEnterPinVisibility(true) else viewState.setEnterPinVisibility(false)
//        viewState.startWebView(token)
    }

    fun onLogout(){
        preferences().token = ""
    }

    fun onResumeForPin(){
        if (preferences().pin!!.isEmpty()) viewState.setEnterPinVisibility(true) else viewState.setEnterPinVisibility(false)
    }

    fun onCancelPinClicked(){
        viewState.onCancelPinClicked()
    }

    fun onCancelPinPositiveClicked(){
        preferences().pin = ""
        viewState.setEnterPinVisibility(true)
    }

    fun updateFirebaseToken(firebaseToken: String){
        var fireTokenBody = FirebaseTokenBody(firebaseToken)
        subscription.add(restApi().iniStudentService.updateFirebaseToken(token, fireTokenBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {onSuccessUpdateFirebaseToken() },
                onError = { Log.d("error->>>", it.message.toString())}
            ))
    }

    private fun onSuccessUpdateFirebaseToken(){
        viewState.startWebView(token)
    }
}