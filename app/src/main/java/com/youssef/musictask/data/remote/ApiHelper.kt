package com.youssef.musictask.data.remote

import com.youssef.musictask.base.DataResult
import com.youssef.musictask.data.pref.model.SavedToken
import com.youssef.musictask.domain.models.Song

interface ApiHelper {
    fun getTokenRequest(): DataResult<SavedToken>
    fun getSongs(searchKey: String, token: String): DataResult<MutableList<Song>>
}