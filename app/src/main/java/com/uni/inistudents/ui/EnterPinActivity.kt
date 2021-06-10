package com.uni.inistudents.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.uni.inistudents.R
import com.uni.inistudents.presenter.EnterPinPresenter
import com.uni.inistudents.utils.AsteriskPasswordTransformationMethod
import com.uni.inistudents.view.EnterPinView
import moxy.presenter.InjectPresenter

class EnterPinActivity : BaseActivity(), EnterPinView {

    @InjectPresenter
    override lateinit var presenter: EnterPinPresenter


    override fun getLayoutResID(): Int = R.layout.activity_enter_pin


    override fun setupView(savedInstanceState: Bundle?) {
        val pinEntry = findViewById<View>(R.id.txt_pin_entry) as PinEntryEditText
        pinEntry.transformationMethod = AsteriskPasswordTransformationMethod()
        pinEntry?.setOnPinEnteredListener { str -> presenter.onEnteredPin(str.toString()) }
    }

    companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, EnterPinActivity::class.java)
    }

    override fun onFinish() {
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }


}