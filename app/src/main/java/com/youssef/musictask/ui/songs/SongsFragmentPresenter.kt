package com.youssef.musictask.ui.songs

import com.youssef.musictask.base.BasePresenter
import com.youssef.musictask.base.DataResult
import com.youssef.musictask.domain.interactors.AppInteractor
import java.util.*


class SongsFragmentPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<SongsFragmentContract.View>(),
    SongsFragmentContract.Presenter {


    override fun isAccessTokenExpired(): Boolean {
        return appInteractor.isAccessTokenExpired()
    }

    override fun getSongs(searchKey: String) {
        view?.showLoading()
        appInteractor.getSongs(searchKey) { result ->
            when (result) {
                is DataResult.Success -> {
                    view?.hideLoading()
                    if (result.data.isEmpty()) {
                        getFilteredSavedSongs(searchKey)
                    } else {
                        view?.onGetSongsSuccess(result.data)
                    }
                }
                is DataResult.Error -> {
                    view?.hideLoading()
                    getFilteredSavedSongs(searchKey)
                    view?.showMessage(result.exception.message ?: "")
                }
            }
        }
    }

    private fun getFilteredSavedSongs(searchKey: String) {
        val savedList = appInteractor.getMockedSongs().map {
            it.copy()
        }.filter { it.title.toLowerCase(Locale.US).contains(searchKey.toLowerCase(Locale.US)) }.toMutableList()
        view?.onGetSongsSuccess(savedList)
    }

    override fun getToken() {
        appInteractor.getTokenRequest { result ->
            when (result) {
                is DataResult.Success -> {
                    view?.onGetTokenSuccess(result.data)
                }
                is DataResult.Error -> {
                    view?.showMessage(result.exception.message ?: "")
                }
            }
        }
    }

}