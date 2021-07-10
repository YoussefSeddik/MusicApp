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
