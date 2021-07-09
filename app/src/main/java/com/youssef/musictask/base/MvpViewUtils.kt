package com.youssef.musictask.base

interface MvpViewUtils {
    fun onNetworkError()
    fun showLoading()
    fun hideLoading()
    fun showMessage(msg:String)
}