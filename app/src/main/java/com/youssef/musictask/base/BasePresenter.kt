package com.youssef.musictask.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

abstract class BasePresenter<V> : MvpPresenter<V>, LifecycleObserver {
    var view: V? = null

    override fun attachView(view: V, lifecycle: Lifecycle?) {
        this.view = view
        lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun deAttachView() {
        this.view = null
    }

    override fun isAttached(): Boolean {
        return view !=null
    }


}