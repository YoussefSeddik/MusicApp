package com.youssef.musictask.ui.songs

import com.youssef.musictask.base.MvpPresenter
import com.youssef.musictask.base.MvpViewUtils
import com.youssef.musictask.data.pref.model.Token
import com.youssef.musictask.domain.models.Song

interface SongsActivityContract {

    interface Presenter : MvpPresenter<View> {
        fun isAccessTokenExpired(): Boolean
        fun getSongs(searchKey: String)
        fun getToken()

    }

    interface View : MvpViewUtils {
        fun onGetSongsSuccess(songs: MutableList<Song>)
        fun onGetTokenSuccess(token: Token)

    }
}