package com.youssef.musictask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youssef.musictask.R
import com.youssef.musictask.data.remote.helpers.image_loader_queue.ImageLoader
import com.youssef.musictask.ui.songs.SongsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, SongsFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        ImageLoader.releaseResources()
    }
}