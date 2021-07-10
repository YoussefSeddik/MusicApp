package com.youssef.musictask.data.pref

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.youssef.musictask.data.pref.model.SavedToken

class PrefHelperImpl private constructor(context: Context) : PrefHelper {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.packageName, Application.MODE_PRIVATE)
    private val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun setToken(savedToken: SavedToken) {
        prefsEditor.apply {
            putString(ARG_TOKEN, savedToken.token)
        }.apply()
        prefsEditor.apply {
            putString(ARG_EXPIRED_AT, savedToken.expiredAt.toString())
        }.apply()
    }

    override fun getToken(): SavedToken {
        val token = SavedToken.empty()
        token.token = sharedPreferences.getString(ARG_TOKEN, "") ?: ""
        token.expiredAt = (sharedPreferences.getString(ARG_EXPIRED_AT, "0") ?: "0").toLong()
        return token
    }

    companion object {
        const val ARG_TOKEN = "ARG_TOKEN"
        const val ARG_EXPIRED_AT = "ARG_EXPIRED_AT"
        private var mInstance: PrefHelperImpl? = null
        fun init(context: Context) {
            mInstance = PrefHelperImpl(context)
        }

        val instance: PrefHelperImpl?
            get() {
                if (mInstance == null) {
                    throw RuntimeException(
                        "Must run init(Application application) before an instance can be obtained"
                    )
                }
                return mInstance
            }
    }
}