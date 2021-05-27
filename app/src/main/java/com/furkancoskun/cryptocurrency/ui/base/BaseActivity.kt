package com.furkancoskun.cryptocurrency.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.furkancoskun.cryptocurrency.data.model.common.AlertStatusType
import com.furkancoskun.cryptocurrency.ext.displayDialog
import com.furkancoskun.cryptocurrency.ext.displayDialogButtonColorCallback
import com.furkancoskun.cryptocurrency.ext.displayDialogWithCallback
import com.furkancoskun.cryptocurrency.ui.DataStateChangeListener
import com.furkancoskun.cryptocurrency.ui.UICommunicationListener
import com.furkancoskun.cryptocurrency.ui.UIMessage
import com.furkancoskun.cryptocurrency.ui.UIMessageType

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), DataStateChangeListener,
    UICommunicationListener {

    lateinit var binding: T

    abstract fun getViewBinding(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

    override fun showAlert(
        statusType: Int,
        message: String,
        shouldShowSuccessAlert: Boolean,
        shouldShowErrorAlert: Boolean
    ) {
        when (statusType) {
            AlertStatusType.SUCCESS.code -> if (shouldShowSuccessAlert) displayDialog(
                message,
                AlertStatusType.SUCCESS
            )
            AlertStatusType.INFO.code -> if (shouldShowErrorAlert) displayDialog(
                message,
                AlertStatusType.INFO
            )
            else -> if (shouldShowErrorAlert) displayDialog(message, AlertStatusType.ERROR)
        }
    }

    override fun onUIMessageReceived(
        uiMessage: UIMessage,
        statusType: Int,
        btnTitle: String?,
        shouldShowCancel: Boolean?
    ) {
        when (uiMessage.uiMessageType) {
            is UIMessageType.Dialog -> {
                displayDialogWithCallback(
                    uiMessage.message,
                    uiMessage.uiMessageType.callback,
                    statusType,
                    btnTitle,
                    shouldShowCancel
                )
            }
            is UIMessageType.None -> {
                Log.d("BaseActivity", "onUIMessageReceived : ${uiMessage.message}")
            }
        }
    }

    override fun onUIMessageReceivedButtonColor(
        uiMessage: UIMessage,
        statusType: Int,
        btnTitle: String?,
        shouldShowCancel: Boolean?
    ) {
        when (uiMessage.uiMessageType) {
            is UIMessageType.DialogButtonColor -> {
                displayDialogButtonColorCallback(
                    uiMessage.message,
                    uiMessage.uiMessageType.callback,
                    statusType,
                    btnTitle,
                    shouldShowCancel
                )
            }
            is UIMessageType.None -> {
                Log.d("BaseActivity", "onUIMessageReceivedButtonColor : ${uiMessage.message}")
            }
        }

    }

    // Progress Bar
    override fun showProgressBar(display: Boolean) {
        displayProgressBar(display)
    }

    abstract fun displayProgressBar(display: Boolean)

    // Hide Keyboard
    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager
                .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    override fun setBottomBarBgColor(color: Int) {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }
}