package com.youssef.musictask.data.pref

import com.youssef.musictask.data.pref.model.Token

interface PrefHelper {
    fun setToken(token: Token)
    fun getToken(): Token
}