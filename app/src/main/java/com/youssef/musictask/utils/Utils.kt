package com.youssef.musictask.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.youssef.musictask.R


object Utils {

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