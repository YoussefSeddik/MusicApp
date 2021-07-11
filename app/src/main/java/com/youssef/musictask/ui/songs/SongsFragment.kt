package com.youssef.musictask.ui.songs

import android.os.Bundle
import android.os.Looper
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.HandlerCompat
import androidx.core.view.ViewCompat
import com.youssef.musictask.R
import com.youssef.musictask.base.BaseFragment
import com.youssef.musictask.data.pref.model.SavedToken
import com.youssef.musictask.data.remote.ApiHelper
import com.youssef.musictask.data.remote.ApiHelperImpl
import com.youssef.musictask.databinding.FragmentSongsBinding
import com.youssef.musictask.databinding.ItemSongBinding
import com.youssef.musictask.domain.interactors.AppInteractorImpl
import com.youssef.musictask.domain.models.Song
import com.youssef.musictask.ui.music_song_details.SongsDetailsFragment
import com.youssef.musictask.ui.songs.adapter.SongsAdapter
import com.youssef.musictask.utils.FuturePromise
import com.youssef.musictask.utils.secretMe
import com.youssef.musictask.utils.showMe
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class SongsFragment : BaseFragment<SongsFragmentContract.Presenter>(),
    SongsFragmentContract.View, SearchView.OnQueryTextListener, SongsAdapter.SongsListener {

    private var mSearchText = ""
    private val mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val poolExecutor: Executor = Executors.newSingleThreadExecutor()
    private val apiHelper: ApiHelper = ApiHelperImpl()
    private val songsAdapter = SongsAdapter(this)
    private lateinit var ui: FragmentSongsBinding
    override val presenter =
        SongsFragmentPresenter(AppInteractorImpl(apiHelper, poolExecutor, mainThreadHandler))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentSongsBinding.inflate(layoutInflater, container, false)
        return ui.root
    }

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        presenter.attachView(this, lifecycle)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        ui.songsRecyclerView.adapter = songsAdapter
    }

    override fun onResume() {
        super.onResume()
        if (presenter.isAttached().not()) {
            presenter.attachView(this, lifecycle)
        }
        checkIfShouldReloadData()
    }

    private fun checkIfShouldReloadData() {
        if (songsAdapter.getSongs().isEmpty()) {
            checkIfTokenIsExpired {
                querySongs()
            }
        } else {
            hideLoading()
            songsAdapter.refreshData()
            checkEmptyState()
        }
    }

    private fun checkIfTokenIsExpired(notExpiredAction: () -> Unit) {
        if (presenter.isAccessTokenExpired()) {
            presenter.getToken()
        } else {
            notExpiredAction()
        }
    }

    private fun querySongs() {
        presenter.getSongs(mSearchText)
    }

    override fun onGetSongsSuccess(songs: MutableList<Song>) {
        songsAdapter.swapData(songs)
        checkEmptyState()
    }

    private fun checkEmptyState() {
        if (songsAdapter.getSongs().isEmpty()) {
            ui.emptyLayout.showMe()
        } else {
            ui.emptyLayout.secretMe()
        }
    }

    override fun onGetTokenSuccess(savedToken: SavedToken) {
        querySongs()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().actionBar?.setTitle(R.string.search_music)
        inflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem?.actionView as (SearchView)
        searchView.setOnQueryTextListener(this)
        searchView.requestFocus()
        searchView.queryHint = getString(R.string.search_for_music)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrBlank().not()) {
            queryTextChanged(newText ?: "")
        }
        return true
    }

    private fun queryTextChanged(newText: String) {
        FuturePromise.startPromiseAfter(1000) {
            if (newText != mSearchText) {
                mSearchText = newText
                checkIfTokenIsExpired {
                    querySongs()
                }
            }
        }
    }

    override fun onSongClicked(song: Song, itemUi: ItemSongBinding) {
        setUpRecyclerViewsItemsUniquesIdentifiers(song, itemUi)
        goToDetailsFragment(song, itemUi)
    }

    private fun setUpRecyclerViewsItemsUniquesIdentifiers(song: Song, itemUi: ItemSongBinding) {
        ViewCompat.setTransitionName(
            itemUi.artistNameLogoTextView,
            song.id
        )
        ViewCompat.setTransitionName(
            itemUi.artistNameTextView,
            song.artistName
        )
        ViewCompat.setTransitionName(
            itemUi.musicImageView,
            song.image
        )
        ViewCompat.setTransitionName(
            itemUi.songTypeTextView,
            song.type
        )
    }

    private fun goToDetailsFragment(song: Song, itemUi: ItemSongBinding) {
        parentFragmentManager.beginTransaction().replace(
            R.id.mainFrameLayout, SongsDetailsFragment.newInstance(song)
        ).addToBackStack(SongsDetailsFragment::class.java.name)
            .addSharedElement(
                itemUi.musicImageView,
                "detailsMusicImageView"
            ).addSharedElement(
                itemUi.artistNameLogoTextView,
                "detailsArtistNameLogoTextView"
            ).addSharedElement(
                itemUi.artistNameTextView,
                "detailsArtistNameTextView"
            ).addSharedElement(
                itemUi.songTypeTextView,
                "detailsSongTypeTextView"
            ).setCustomAnimations(
                R.anim.slide_in_right_to_left,
                R.anim.slide_out_right_to_left,
                R.anim.slide_in_left_to_right,
                R.anim.slide_out_left_to_right,
            ).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        ui.songsRecyclerView.adapter = null
    }
}