package com.youssef.musictask.data.pref.model

import java.io.Serializable
import java.util.*

data class Token(
    var expiredAt: Long,
    var token: String
) : Serializable {
    fun isExpired(): Boolean {
        return Calendar.getInstance().timeInMillis > expiredAt
    }

    companion object {
        fun empty() = Token(expiredAt = 0, token = "")
    }
}
