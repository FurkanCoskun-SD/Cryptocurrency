package com.furkancoskun.cryptocurrency.ext

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import com.furkancoskun.cryptocurrency.R
import com.furkancoskun.cryptocurrency.data.model.common.AlertStatusType
import kotlinx.android.synthetic.main.layout_alert.*

interface AlertCallback {
    fun proceed()
    fun cancel()
}

interface AlertCallbackWithParamethers {
    fun proceed(amount: String)
    fun cancel()
}

interface AlertCallbackWithButtonColor {
    fun proceed()
    fun cancel()
}

fun Activity.displayDialog(message: String?, alertStatusType: AlertStatusType) {

    val dialog = Dialog(this, R.style.CustomDialogTheme)
    dialog.setContentView(R.layout.layout_alert)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)

    dialog.alert_title.text = message

    val animation = dialog.lottie_animation
    animation.playAnimation()

    when (alertStatusType) {
        AlertStatusType.SUCCESS -> animation.setAnimation(getString(R.string.alert_type_done_icon))
        AlertStatusType.ERROR -> animation.setAnimation(getString(R.string.alert_type_warning_icon))
        AlertStatusType.INFO -> animation.setAnimation(getString(R.string.alert_type_info_icon))
    }

    val positiveButton = dialog.findViewById<TextView>(R.id.positive_button)

    positiveButton.setOnClickListener {
        animation.cancelAnimation()
        dialog.dismiss()
    }

    dialog.show()
}

fun Activity.displayDialogWithCallback(
    errorMessage: String?, callback: AlertCallback, statusType: Int, btnTitle: String? = getString(
        R.string.BtnOkay
    ), shouldShowCancel: Boolean? = false
) {

    val dialog = Dialog(this, R.style.CustomDialogTheme)

    dialog.setContentView(R.layout.layout_alert)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)

    dialog.alert_title.text = errorMessage ?: ""

    dialog.positive_button.text = btnTitle

    val animation = dialog.lottie_animation

    when (statusType) {
        AlertStatusType.SUCCESS.code -> animation.setAnimation(getString(R.string.alert_type_done_icon))
        AlertStatusType.ERROR.code -> animation.setAnimation(getString(R.string.alert_type_warning_icon))
        AlertStatusType.INFO.code -> animation.setAnimation(getString(R.string.alert_type_info_icon))
    }

    animation.playAnimation()

    dialog.positive_button.setOnClickListener {
        animation.cancelAnimation()
        callback.proceed()
        dialog.dismiss()
    }

    val negativeButton = dialog.negative_button

    shouldShowCancel?.let {
        negativeButton.visibility = View.VISIBLE
        negativeButton.setOnClickListener {
            callback.cancel()
            dialog.dismiss()
        }
    }

    dialog.show()
}

fun Activity.displayDialogButtonColorCallback(
    errorMessage: String?,
    callback: AlertCallbackWithButtonColor,
    statusType: Int,
    btnTitle: String? = getString(
        R.string.BtnOkay
    ),
    shouldShowCancel: Boolean? = false
) {

    val dialog = Dialog(this, R.style.CustomDialogTheme)

    dialog.setContentView(R.layout.layout_alert_cancel)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)

    dialog.alert_title.text = errorMessage ?: ""

    dialog.positive_button.text = btnTitle

    val animation = dialog.lottie_animation

    when (statusType) {
        AlertStatusType.SUCCESS.code -> animation.setAnimation(getString(R.string.alert_type_done_icon))
        AlertStatusType.ERROR.code -> animation.setAnimation(getString(R.string.alert_type_warning_icon))
        AlertStatusType.INFO.code -> animation.setAnimation(getString(R.string.alert_type_info_icon))
    }

    animation.playAnimation()

    dialog.positive_button.setOnClickListener {
        animation.cancelAnimation()
        callback.proceed()
        dialog.dismiss()
    }

    val negativeButton = dialog.negative_button

    shouldShowCancel?.let {
        negativeButton.visibility = View.VISIBLE
        negativeButton.setOnClickListener {
            callback.cancel()
            dialog.dismiss()
        }
    }

    dialog.show()
}

