package com.youssef.musictask.data.pref.model

import java.io.Serializable
import java.util.*

data class SavedToken(
    var expiredAt: Long,
    var token: String
) : Serializable {
    fun isExpired(): Boolean {
        return Calendar.getInstance().timeInMillis > expiredAt
    }

    companion object {
        fun empty() = SavedToken(expiredAt = 0, token = "")
    }
}
