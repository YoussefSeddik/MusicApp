package com.youssef.musictask

import android.app.Application
import com.youssef.musictask.data.pref.PrefHelperImpl


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        PrefHelperImpl.init(applicationContext)
    }

}