package com.uni.inistudents.presenter

import android.util.Log
import com.uni.inistudents.model.DataInfo
import com.uni.inistudents.model.StudentAuth
import com.uni.inistudents.utils.isEmpty
import com.uni.inistudents.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState

@InjectViewState
class MainPresenter(private var isLogout: Boolean = false) : BasePresenter<MainView>() {

    private var uni: DataInfo? = null

    init {
//        if (!isGuest(preferences())!!){
//            viewState.startWebViewScreen(preferences().token!!)
//        }

        if (preferences().universityId!!.isNotEmpty()) {
            Log.e("url->>", preferences().universityUrl.toString())
            this.uni = DataInfo(
                preferences().universityId ?: "",
                preferences().universityTitle ?: "",
                preferences().universityTitleEn ?: "",
                preferences().universityUrl ?: ""
            )
            viewState.setMainViewVisibility(this.uni)
        }

        if (checkIfPinExist()) {
            if (isLogout) viewState.setEnterPinViewVisibility(false) else viewState.setEnterPinViewVisibility(
                true
            )
        } else viewState.setEnterPinViewVisibility(false)

        viewState.addAdView()

    }

    fun openUniversityClicked() {
        viewState.onOpenUniversityClicked()
    }

    fun onSignInClicked(user: String, password: String, uni: DataInfo) {
        this.uni = DataInfo(uni.id, uni.title, uni.title_en, uni.url)
        Log.e("id->>>", uni.id)
        if (isEmpty(user) || isEmpty(password) || isEmpty(uni.id)) viewState.onEmptyAuth() else loadStudent(
            user,
            password,
            uni.id,
        )
    }

    fun onCancelEnterPin() {
        viewState.setEnterPinViewVisibility(false)
    }

    fun onEnteredPin(pin: String) {
        if (preferences().pin!!.isNotEmpty()) {
            if (preferences().pin == pin) {
                viewState.startWebViewScreen(preferences().token!!, preferences().universityUrl!!)
            } else viewState.showWrongPinMessage()
        }
    }

    private fun loadStudent(user: String, password: String, uni: String) {
        subscription.add(restApi().iniStudentService.studentAuth(user, password, uni)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { onSuccessLoadStudent(it) },
                onError = { Log.d("error->>>", it.message.toString()) }
            ))
    }

    private fun onSuccessLoadStudent(student: StudentAuth) {
        when (student.result) {
            "yes" -> {
                saveUni()
                preferences().token = student.token
                viewState.startWebViewScreen(student.token)
            }
            "no" -> {
                viewState.onEmptyAuth()
            }
        }
    }

    fun saveUniWhenLanguageChanged(uni: DataInfo) {
        preferences().universityId = uni.id
        preferences().universityTitle = uni.title
        preferences().universityTitleEn = uni.title_en
        preferences().universityUrl = uni.url
    }

    private fun saveUni() {
        preferences().universityId = this.uni?.id ?: ""
        preferences().universityTitle = this.uni?.title ?: ""
        preferences().universityTitleEn = this.uni?.title_en ?: ""
        preferences().universityUrl = this.uni?.url ?: ""
    }

    private fun checkIfPinExist(): Boolean = preferences().pin!!.isNotEmpty()
}
