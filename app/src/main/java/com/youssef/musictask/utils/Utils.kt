package com.youssef.musictask.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.youssef.musictask.R
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    fun longToString(timeInMillis: Long): String {
        val fullDateFormat = "dd-MMM-yy hh.mm aa"
        val simpleDatFormat = SimpleDateFormat(fullDateFormat, Locale.US)
        return simpleDatFormat.format(Date(timeInMillis)).toString()
    }

    fun parseStringToReadableData(stringDate: String): String {
        val simpleDateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            Locale.US
        )
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val publishedAtInMillis =
            simpleDateFormat.parse(stringDate)?.time ?: 0L
        return longToString(publishedAtInMillis)
    }

    fun getDurationInMinutesAndSecondsFormat(stringSeconds: String): String {
        if (stringSeconds.isBlank()) return ""
        val secondsInLong = stringSeconds.toLong()
        val minutes = (secondsInLong % 3600) / 60
        val remainingSeconds = secondsInLong % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    fun showLoadingDialog(context: Context, withOutShadow: Boolean = true): AlertDialog? {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        val builder = context.let { it1 ->
            AlertDialog.Builder(it1).setView(mDialogView)
        }.show()
        builder.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (withOutShadow) {
                clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }
        return builder
    }


    fun getRandomString(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }


}