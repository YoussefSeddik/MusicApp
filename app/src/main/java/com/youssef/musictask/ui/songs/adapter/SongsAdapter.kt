package com.youssef.musictask.ui.songs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.youssef.musictask.R
import com.youssef.musictask.data.remote.helpers.image_loader_queue.ImageLoader
import com.youssef.musictask.databinding.ItemSongBinding
import com.youssef.musictask.domain.models.Song

class SongsAdapter(private val listener: SongsListener) :
    RecyclerView.Adapter<SongsAdapter.SongVH>() {

    private var mSongs = mutableListOf<Song>()
    fun getSongs() = mSongs
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongVH {
        val binding = DataBindingUtil.inflate<ItemSongBinding>(
            LayoutInflater.from(parent.context), R.layout.item_song, parent, false
        )
        return SongVH(binding)
    }

    override fun onBindViewHolder(holder: SongVH, position: Int) {
        holder.bind(mSongs[position])
    }

    override fun getItemCount(): Int = mSongs.size

    inner class SongVH(private val itemUi: ItemSongBinding) : RecyclerView.ViewHolder(itemUi.root) {
        init {
            itemUi.root.setOnClickListener {
                ViewCompat.setTransitionName(
                    itemUi.musicImageView,
                    ""
                )
                ViewCompat.setTransitionName(
                    itemUi.artistNameLogoTextView,
                    ""
                )
                listener.onSongClicked(mSongs[adapterPosition], itemUi)
            }
        }

        fun bind(song: Song) {
            itemUi.song = song
            ImageLoader.load(song.image).into(itemUi.musicImageView)
        }
    }

    fun swapData(songs: MutableList<Song>) {
        mSongs = songs
        notifyDataSetChanged()
    }

    fun refreshData() {
        notifyDataSetChanged()
    }

    interface SongsListener {
        fun onSongClicked(song: Song, itemUi: ItemSongBinding)
    }

}