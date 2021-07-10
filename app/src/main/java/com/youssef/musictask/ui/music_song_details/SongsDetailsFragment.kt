package com.youssef.musictask.ui.music_song_details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youssef.musictask.R
import com.youssef.musictask.data.remote.helpers.ImageToLoad
import com.youssef.musictask.data.remote.helpers.image_loader_queue.ImageLoaderQueue
import com.youssef.musictask.databinding.FragmentSongsDetailsBinding
import com.youssef.musictask.domain.models.Song


class SongsDetailsFragment : Fragment() {
    private lateinit var ui: FragmentSongsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentSongsDetailsBinding.inflate(layoutInflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val song = requireArguments().getSerializable(ARG_SONG) as Song
        setUpSongDetails(song)
    }

    private fun setUpSongDetails(song: Song) = with(ui) {
        ui.song = song
        ImageLoaderQueue.push(ImageToLoad(ui.musicImageView, song.image))
        ImageLoaderQueue.execute()
    }

    companion object {
        private const val ARG_SONG = "ARG_SONG"
        fun newInstance(song: Song) =
            SongsDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_SONG, song)
                }
            }
    }
}