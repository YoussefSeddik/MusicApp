package com.youssef.musictask.ui.songs

import com.youssef.musictask.base.BasePresenter
import com.youssef.musictask.base.DataResult
import com.youssef.musictask.domain.interactors.AppInteractor


class SongsActivityPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<SongsActivityContract.View>(),
    SongsActivityContract.Presenter {


    override fun isAccessTokenExpired(): Boolean {
        return appInteractor.isAccessTokenExpired()
    }

    override fun getSongs(searchKey: String) {
        appInteractor.getSongs(searchKey) { result ->
            when (result) {
                is DataResult.Success -> {
                    view?.onGetSongsSuccess(result.data)
                }
                is DataResult.Error -> {
                    view?.showMessage(result.exception.message ?: "")
                }
            }
        }
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