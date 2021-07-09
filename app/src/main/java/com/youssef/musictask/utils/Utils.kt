package com.youssef.musictask.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.LocaleList
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.youssef.musictask.R
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    const val FULL_DATE_FORMAT = "dd-MMM-yy hh.mm aa"

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

    fun fullScreen(activity: Activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


    fun longToString(timeInMillis: Long, dateFormat: String): String {
        val simpleDatFormat = SimpleDateFormat(dateFormat, Locale.US)
        return simpleDatFormat.format(Date(timeInMillis)).toString()
    }

    fun createImageFile(context: Context): File? {
        val fileFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
        val fileExtension = "jpg"
        val timeStamp: String = SimpleDateFormat(fileFormat, Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".$fileExtension", storageDir)
    }

    fun createFileFromBitmap(context: Context, bitmap: Bitmap): File? {
        val fileFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
        val timeStamp: String = SimpleDateFormat(fileFormat, Locale.US).format(Date())
        val path: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(path, "WeatherStatus$timeStamp.jpg")
        var fileOutPutStream: FileOutputStream? = null
        try {
            fileOutPutStream = FileOutputStream(file)
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                85,
                fileOutPutStream
            )
        } catch (e: FileNotFoundException) {
            Log.e(
                "Elgoe",
                e.localizedMessage ?: ""
            )
        } finally {
            if (fileOutPutStream != null) {
                try {
                    fileOutPutStream.flush()
                    fileOutPutStream.getFD().sync()
                    fileOutPutStream.close()
                } catch (e: IOException) {
                    Log.e(
                        "Elgoe",
                        e.localizedMessage ?: ""
                    )
                }
            }
        }
        MediaStore.Images.Media.insertImage(
            context.contentResolver,
            file.absolutePath,
            file.name,
            file.name
        )
        return file
    }

    fun getRandomString(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }




}