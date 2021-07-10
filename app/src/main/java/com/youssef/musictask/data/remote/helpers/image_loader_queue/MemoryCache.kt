package com.youssef.musictask.data.remote.helpers.image_loader_queue

import android.graphics.Bitmap
import java.lang.ref.SoftReference
import java.util.*
import kotlin.collections.HashMap


class MemoryCache {
    private val cache: MutableMap<String, SoftReference<Bitmap>> =
        Collections.synchronizedMap(HashMap<String, SoftReference<Bitmap>>())

    operator fun get(id: String): Bitmap? {
        if (!cache.containsKey(id)) return null
        val ref: SoftReference<Bitmap>? = cache[id]
        return ref?.get()
    }

    fun put(id: String, bitmap: Bitmap?) {
        cache[id] = SoftReference<Bitmap>(bitmap)
    }

    fun clear() {
        cache.clear()
    }
}