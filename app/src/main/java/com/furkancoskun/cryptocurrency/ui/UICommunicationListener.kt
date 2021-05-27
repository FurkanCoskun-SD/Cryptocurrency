package com.furkancoskun.cryptocurrency.ui

import com.furkancoskun.cryptocurrency.ext.AlertCallback
import com.furkancoskun.cryptocurrency.ext.AlertCallbackWithButtonColor

interface UICommunicationListener {
    fun onUIMessageReceived(
        uiMessage: UIMessage,
        statusType: Int,
        btnTitle: String? = null,
        shouldShowCancel: Boolean? = null
    )

    fun onUIMessageReceivedParamethers(
        uiMessage: UIMessage,
        statusType: Int,
        btnTitle: String? = null,
        shouldShowCancel: Boolean? = null
    )

    fun onUIMessageReceivedButtonColor(
        uiMessage: UIMessage,
        statusType: Int,
        btnTitle: String? = null,
        shouldShowCancel: Boolean? = null
    )
}

data class UIMessage(
    val message: String,
    val uiMessageType: UIMessageType
)

sealed class UIMessageType {

    class Dialog(
        val callback: AlertCallback
    ) : UIMessageType()

    class DialogButtonColor(
        val callback: AlertCallbackWithButtonColor
    ) : UIMessageType()

    class None : UIMessageType()

}