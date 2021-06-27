package com.uni.inistudents.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.google.firebase.iid.FirebaseInstanceId
import com.uni.inistudents.R
import com.uni.inistudents.presenter.WebViewPresenter
import com.uni.inistudents.view.WebViewView
import kotlinx.android.synthetic.main.activity_web_view.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class WebViewActivity : BaseActivity(), WebViewView {

    @InjectPresenter
    override lateinit var presenter: WebViewPresenter

    @ProvidePresenter
    fun providePresenter() = WebViewPresenter(
        intent.getStringExtra("token") as String,
        intent.getStringExtra("url") as String
    )

    override fun getLayoutResID(): Int = R.layout.activity_web_view

    override fun setupView(savedInstanceState: Bundle?) {
        ivLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setMessage(R.string.logout_message)

            builder.setPositiveButton(R.string.answer_yes) { dialog, which ->
                presenter.onLogout()
                startActivity(MainActivity.createIntent(this@WebViewActivity, true))
                finish()
            }

            builder.setNegativeButton(R.string.answer_no) { dialog, which ->
                dialog.dismiss()
            }

            builder.show()
        }

        ivCancelPin.setOnClickListener { presenter.onCancelPinClicked() }
        ivEnterPin.setOnClickListener { startActivityForResult(EnterPinActivity.createIntent(this@WebViewActivity), 0) }
        firebaseMessaging()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun startWebView(token: String, url: String) {
        webView.apply {
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            loadUrl(url)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(
                    view: WebView?,
                    url: String?
                ) {
                    super.onPageFinished(view, url)
                    val key = "Student-Token"
                    val `val` = token

                    if (Uri.parse(url).toString()
                            .contains("emis1.seu.edu.ge") || Uri.parse(url).toString().contains("vici.gtu.ge")
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webView.evaluateJavascript(
                                "localStorage.setItem('$key','$`val`');",
                                null
                            )
                        } else {
                            webView.loadUrl("javascript:localStorage.setItem('$key','$`val`');")
                        }
                    } else {
                        webView.goBack()
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    }

                }
            }
        }
    }

    private fun firebaseMessaging(){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    presenter.updateFirebaseToken(task.result?.token ?: "")
            }
    }

    override fun setEnterPinVisibility(isVisible: Boolean) {
        if (isVisible){
            ivEnterPin.visibility = View.VISIBLE
            ivCancelPin.visibility = View.GONE
        } else {
            ivEnterPin.visibility = View.GONE
            ivCancelPin.visibility = View.VISIBLE
        }
    }

    override fun onCancelPinClicked() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.cancel_pin_message)
        builder.setPositiveButton(R.string.answer_yes) { dialog, which -> presenter.onCancelPinPositiveClicked() }
        builder.setNegativeButton(R.string.answer_no) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResumeForPin()
    }


    companion object{
        fun createIntent(context: Context, token: String, url: String): Intent =
            Intent(context, WebViewActivity::class.java)
                .putExtra("token", token)
                .putExtra("url", url)
    }
}