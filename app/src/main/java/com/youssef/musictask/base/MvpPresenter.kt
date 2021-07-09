package com.youssef.musictask.base

import androidx.lifecycle.Lifecycle

interface MvpPresenter<V> {
    fun isAttached(): Boolean
    fun attachView(view: V, lifecycle: Lifecycle?)
    fun deAttachView()
}