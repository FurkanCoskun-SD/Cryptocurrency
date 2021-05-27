package com.furkancoskun.cryptocurrency.ext

import android.content.Context
import android.graphics.Color.green
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.furkancoskun.cryptocurrency.R
import com.furkancoskun.cryptocurrency.data.model.common.AppError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.text.DecimalFormat

fun Double.priceFormat(): String {
    return this.let {
        val numberFormat = DecimalFormat("#,##0.00")
        "$ ${numberFormat.format(this)}"
    }
}

fun Exception.showError(context: Context?): String {
    when (this) {
        is HttpException -> {
            val response = this.response()
            val type = object : TypeToken<AppError>() {}.type
            try {
                val appError: AppError = Gson().fromJson(response?.errorBody()?.charStream(), type)
                return appError.convertServerResponse(context)
            } catch (e: Exception) {
                context?.let {
                    return context.getString(R.string.ERROR)
                } ?: return "Bir hata oluştu. Lütfen tekrar deneyin"

            }
        }
        else -> return "Bir hata oluştu. Lütfen tekrar deneyin"
    }
}

fun AppError.convertServerResponse(context: Context?): String {
    return try {
        context!!.getString(
            context.resources.getIdentifier(
                this.code,
                "string",
                context.packageName
            )
        )
    } catch (e: Exception) {
        this.message
    }
}

fun Fragment.navigate(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun String?.trimParanthesis(): String {
    return this?.replace(Regex("[()]"), "") ?: ""
}
