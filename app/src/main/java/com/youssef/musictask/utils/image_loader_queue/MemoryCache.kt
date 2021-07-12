package com.youssef.musictask.utils.image_loader_queue

import android.graphics.Bitmap
import java.lang.ref.SoftReference


class MemoryCache {
    private val cache: MutableMap<String, SoftReference<Bitmap>> = HashMap()

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