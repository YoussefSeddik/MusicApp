package com.youssef.musictask.domain.interactors

import com.youssef.musictask.base.DataResult
import com.youssef.musictask.data.pref.model.SavedToken
import com.youssef.musictask.domain.models.Song

interface AppInteractor {
    fun isAccessTokenExpired(): Boolean
    fun getTokenRequest(callback: (DataResult<SavedToken>) -> Unit)
    fun getSongs(searchKey: String, callback: (DataResult<MutableList<Song>>) -> Unit)
    fun getMockedSongs(): MutableList<Song>
}