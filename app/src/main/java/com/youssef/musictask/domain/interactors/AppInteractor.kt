package com.youssef.musictask.domain.interactors

import com.youssef.musictask.base.DataResult
import com.youssef.musictask.data.pref.model.Token
import com.youssef.musictask.domain.models.Song

interface AppInteractor {
    fun isAccessTokenExpired(): Boolean
    fun getTokenRequest(callback: (DataResult<Token>) -> Unit)
    fun getSongs(searchKey: String, callback: (DataResult<MutableList<Song>>) -> Unit)
}