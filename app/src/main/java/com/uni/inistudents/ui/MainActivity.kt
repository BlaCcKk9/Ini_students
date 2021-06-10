package com.uni.inistudents.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.uni.inistudents.R
import com.uni.inistudents.listener.OnSelectedUniversity
import com.uni.inistudents.model.DataInfo
import com.uni.inistudents.presenter.MainPresenter
import com.uni.inistudents.utils.AsteriskPasswordTransformationMethod
import com.uni.inistudents.utils.language
import com.uni.inistudents.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.*


class MainActivity : BaseActivity(), MainView, OnSelectedUniversity {

    private var universityId: String = ""
    private var uniData: DataInfo = DataInfo("", "", "", "")

    @InjectPresenter
    override lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = MainPresenter(intent.getBooleanExtra("is_logout", false))

    override fun getLayoutResID(): Int = R.layout.activity_main


    override fun setupView(savedInstanceState: Bundle?) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        setLanguageBtnVisibility()
        val pinEntry = findViewById<View>(R.id.txt_pin_entry) as PinEntryEditText
        pinEntry.transformationMethod = AsteriskPasswordTransformationMethod()
        etPassword.transformationMethod = AsteriskPasswordTransformationMethod()
        layoutUniversity.setOnClickListener {
            presenter.openUniversityClicked()
            layoutUniversity.isClickable = false
            layoutUniversity.isActivated = false
        }
        languageEnglish.setOnClickListener {
            language = "en"
            setLanguageBtnVisibility()
            setLocale("ka")
            startActivity(createIntent(this@MainActivity, true))
            finish()
        }

        languageGeorgia.setOnClickListener {
            language = "ge"
            setLanguageBtnVisibility()
            setLocale("en")
            startActivity(createIntent(this@MainActivity, true))
            finish()
        }

        btnSignIn.setOnClickListener { presenter.onSignInClicked(
            etUser.text.toString(),
            etPassword.text.toString(),
            uniData
        ) }
        container.setOnClickListener { presenter.onCancelEnterPin() }


        pinEntry?.setOnPinEnteredListener { str ->
            presenter.onEnteredPin(str.toString())
        }

        tvError.setOnClickListener { tvError.visibility = View.GONE }

    }

    companion object {
        fun createIntent(context: Context, isLogout: Boolean): Intent =
            Intent(context, MainActivity::class.java)
                .putExtra("is_logout", isLogout)
    }

    override fun onOpenUniversityClicked() {
        tvError.visibility = View.GONE
        val dialogFragment = UniversityListDialogFragment()
        val fm = this@MainActivity.supportFragmentManager
        val args = Bundle()
        args.putString("UNI_ID", universityId)
        dialogFragment.arguments = args
        dialogFragment.show(fm, "TAG")
    }

    override fun setMainViewVisibility(university: DataInfo?) {
        uniData = university!!
        Log.e("uniGE->>", university.title)
        Log.e("uniEN->>", university.title_en)
        tvUniversity.text = if (language == "ge") university.title else university.title_en
        layoutUser.visibility = View.VISIBLE
        layoutPassword.visibility = View.VISIBLE
        btnSignIn.visibility = View.VISIBLE
    }

    override fun onEmptyAuth() {
        tvError.visibility = View.VISIBLE
    }

    override fun startWebViewScreen(token: String) {
        tvError.visibility = View.GONE
        startActivity(WebViewActivity.createIntent(this, token))
    }

    override fun setEnterPinViewVisibility(isVisible: Boolean) {
        if (isVisible){
            backGround.visibility = View.VISIBLE
            constraintEnterPin.visibility = View.VISIBLE
        } else {
            backGround.visibility = View.GONE
            constraintEnterPin.visibility = View.GONE
        }
    }

    override fun showWrongPinMessage() {
        Toast.makeText(
            this@MainActivity,
            applicationContext.getString(R.string.wrong_pin),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun addAdView() {

    }

    override fun onSelectedUniversity(university: DataInfo?) {
        layoutUniversity.isClickable = true
        layoutUniversity.isActivated = true
        if (university != null) {
            presenter.saveUniWhenLanguageChanged(university)
            uniData = university
            universityId = university.id
            tvUniversity.text = if (language == "ge") university.title else university.title_en
            layoutUser.visibility = View.VISIBLE
            layoutPassword.visibility = View.VISIBLE
            btnSignIn.visibility = View.VISIBLE
        } else if (layoutUser.visibility == View.VISIBLE) {
            tvUniversity.text = getString(R.string.open_university)
            layoutUser.visibility = View.GONE
            layoutPassword.visibility = View.GONE
            btnSignIn.visibility = View.GONE
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    private fun setLanguageBtnVisibility() {

        if (language == "ge") {
            languageEnglish.visibility = View.VISIBLE
            languageGeorgia.visibility = View.GONE
        } else {
            languageEnglish.visibility = View.GONE
            languageGeorgia.visibility = View.VISIBLE
        }
    }
}