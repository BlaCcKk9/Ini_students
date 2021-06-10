package com.uni.inistudents.presenter

import com.uni.inistudents.view.BaseView
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter

abstract class BasePresenter<T : BaseView> : MvpPresenter<T>() {

    private var utilityWrapper: UtilityWrapper = UtilityWrapper()

    val subscription: CompositeDisposable = CompositeDisposable()

    fun restApi() = utilityWrapper.restApi

    fun applicationContext() = utilityWrapper.applicationContext

    fun preferences() = utilityWrapper.preferences

    private var isResume: Boolean = false
    private var isOpenNewScreenFlow = false


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun onDestroy() {
        subscription.dispose()
        super.onDestroy()
    }


//    override fun onHttpError(error: HttpError) {
//        when {
//            error.isUnauthorized() -> {
//                viewState.toastShort(R.string.network_error_unauthorized)
//            }
//            error.isServerError() -> {
//                viewState.toastShort(R.string.network_error_server)
//            }
//            !TextUtils.isEmpty(error.message) -> {
//                viewState.toastShort(error.message!!)
//            }
//        }
//    }
//
//    override fun onNoInternetConnection() {
//        viewState.showNoInternetConnectionError()
//    }
//
//    override fun onGenericError(t: Throwable?) {
//        if (t != null && t.localizedMessage != null) {
//            viewState.toastLong(t.localizedMessage.orEmpty())
//        }
//    }
//
//    override fun onHttpErrors(errors: Collection<HttpError>) {
//        var msg = ""
//        errors.map { it.message }
//            .forEach {
//                msg += it
//            }
//
//        if (!TextUtils.isEmpty(msg)) {
//            viewState.toastLong(msg)
//        }
//    }

    open fun onResume() {
        isResume = true
    }

    open fun onPause() {
        isResume = false
    }

    open fun handleError(t: Throwable?) {
//        viewState.hideProgress()
        t?.printStackTrace()
//        handleNetworkError(applicationContext(), t, this)
    }

}