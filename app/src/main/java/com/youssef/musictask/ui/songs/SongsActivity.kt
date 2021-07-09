package com.youssef.musictask.ui.songs

import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.core.os.HandlerCompat
import com.youssef.musictask.R
import com.youssef.musictask.base.BaseActivity
import com.youssef.musictask.data.pref.model.Token
import com.youssef.musictask.domain.interactors.AppInteractorImpl
import com.youssef.musictask.domain.models.Song
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class SongsActivity : BaseActivity<SongsActivityContract.Presenter>(),
    SongsActivityContract.View {

    private val mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val poolExecutor: Executor = Executors.newSingleThreadExecutor()
    override val presenter =
        SongsActivityPresenter(AppInteractorImpl(poolExecutor, mainThreadHandler))


    override fun getLayoutResource(): Int = R.layout.activity_songs

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        presenter.attachView(this, lifecycle)
    }

    override fun onResume() {
        super.onResume()
        if (presenter.isAttached().not()) {
            presenter.attachView(this, lifecycle)
        }
        checkIfTokenIsExpired()
    }

    private fun checkIfTokenIsExpired() {
        if (presenter.isAccessTokenExpired()) {
            presenter.getToken()
        } else {
            presenter.getSongs("yy")
        }
    }

    override fun onGetSongsSuccess(songs: MutableList<Song>) {
        findViewById<TextView>(R.id.tokenTextView).text = songs.first().artistName
    }

    override fun onGetTokenSuccess(token: Token) {
        findViewById<TextView>(R.id.tokenTextView).text = token.token
        presenter.getSongs(token.token)
    }


}