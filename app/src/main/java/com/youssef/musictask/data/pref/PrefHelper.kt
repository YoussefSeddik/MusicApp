package com.youssef.musictask.data.pref

import com.youssef.musictask.data.pref.model.SavedToken

interface PrefHelper {
    fun setToken(savedToken: SavedToken)
    fun getToken(): SavedToken
}