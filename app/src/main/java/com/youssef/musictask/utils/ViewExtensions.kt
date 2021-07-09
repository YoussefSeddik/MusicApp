package com.youssef.musictask.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup


fun View.showMe() {
    this.visibility = View.VISIBLE
}

fun View.secretMe() {
    this.visibility = View.GONE
}

fun View.hideMe() {
    this.visibility = View.INVISIBLE
}

fun ViewGroup.generateBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.RGB_565)
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    return bitmap
}
