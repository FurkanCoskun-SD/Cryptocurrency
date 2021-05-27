package com.furkancoskun.cryptocurrency.utils

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.furkancoskun.cryptocurrency.R
import com.furkancoskun.cryptocurrency.ext.trimParanthesis

object UIHelper {

    fun showChangePercent(textView: TextView, _change: Double?) {
        val changePercent = "%.2f %%".format(_change).trimParanthesis()

        textView.text = changePercent
        val context = textView.context
        if (changePercent.contains("-")) {
            textView.setTextColor(
                ContextCompat.getColor(context, R.color.red)
            )
        } else {
            textView.setTextColor(
                ContextCompat.getColor(context, R.color.green)
            )
        }
    }
}