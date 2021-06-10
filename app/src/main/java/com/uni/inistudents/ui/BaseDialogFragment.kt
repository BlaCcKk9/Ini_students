package com.uni.inistudents.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uni.inistudents.R
import com.uni.inistudents.presenter.BasePresenter
import com.uni.inistudents.view.BaseView
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatDialogFragment
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

abstract class BaseDialogFragmentView : MvpAppCompatDialogFragment(), BaseView {

    abstract val presenter: BasePresenter<out BaseView>

    private var DEFAULT_LOADING_MESSAGE = ""

//    private var progressView = CustomProgressScreen()

    private val noInternetConnectionSnackbar by lazy {
        Snackbar.make(
                activity!!.window.decorView.findViewById(android.R.id.content),
                R.string.msg_error_no_internet_connection, Snackbar.LENGTH_LONG
        ).setAction(R.string.settings) { showInternetSettingsScreen() }
    }

    abstract fun getLayoutResID(): Int

    abstract fun setupView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        DEFAULT_LOADING_MESSAGE = getString(R.string.label_loading)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResID(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

//    override fun showProgress() {
//        progressView.message = DEFAULT_LOADING_MESSAGE
//        progressView.updateVisibility(childFragmentManager, true)
//    }
//
//    override fun showProgress(message: String) {
//        progressView.message = message
//        progressView.updateVisibility(childFragmentManager, true)
//    }
//
//    override fun showProgress(textResId: Int) {
//        showProgress(getString(textResId))
//    }
//
//    override fun hideProgress() {
//        progressView.updateVisibility(childFragmentManager, false)
//    }

    override fun toastShort(msg: String) {
        activity?.toast(msg)
    }

    override fun toastShort(resId: Int) {
        activity?.toast(resId)
    }

    override fun toastLong(msg: String) {
        activity?.longToast(msg)
    }

    override fun toastLong(resId: Int) {
        activity?.longToast(resId)
    }

    override fun showNoInternetConnectionError() {
        if (activity == null) {
            return
        }
        if (!noInternetConnectionSnackbar.isShown)
            noInternetConnectionSnackbar.show()
    }

//    override fun showOrderAlreadyCreatedAlert() {
//        AlertDialog.Builder(requireContext())
//                .setMessage(R.string.msg_order_already_created)
//                .setCancelable(false)
//                .setPositiveButton(R.string.btn_return_to_order_list) { dialogInterface, _ ->
//                    startActivity(MainActivity.createIntentInNewTask(requireContext(), openOrders = true))
//                    dialogInterface.dismiss()
//                }
//                .show()
//    }
//
//    override fun showOrderNotFoundAlert() {
//        AlertDialog.Builder(requireContext())
//                .setMessage(R.string.msg_order_not_found)
//                .setCancelable(false)
//                .setPositiveButton(R.string.btn_return_to_home_screen) { dialogInterface, _ ->
//                    startActivity(MainActivity.createIntentInNewTask(requireContext()))
//                    dialogInterface.dismiss()
//                }
//                .show()
//    }
//
//    override fun showOtherDeviceSignedInAlert() {
//        android.app.AlertDialog.Builder(requireContext())
//                .setMessage(R.string.msg_other_device_signed_in)
//                .setCancelable(false)
//                .setPositiveButton(R.string.dialog_btn_ok) { _, _ -> presenter.onConfirmNewAuthOnOtherDevice() }
//                .show()
//    }
//
//    override fun openSplashScreenInNewFlow() {
//        // realized in activity
//    }
//
//    override fun openCreateReviewScreen(orderId: Int) {
//        // realized in activity
//    }

    private fun showInternetSettingsScreen() {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }

    fun setupMenu(){
//        setHasOptionsMenu(true)
    }
}