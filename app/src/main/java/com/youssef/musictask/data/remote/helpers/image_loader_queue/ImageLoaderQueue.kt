package com.youssef.musictask.data.remote.helpers.image_loader_queue

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.core.os.HandlerCompat
import com.youssef.musictask.data.remote.helpers.ImageToLoad
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.Executors

object ImageLoaderQueue {
    private var isExecuting = false
    private var memoryCache: MemoryCache = MemoryCache()
    private val tasksQueue: Queue<ImageToLoad> = LinkedList()
    private val savedImagesWithUrls: MutableMap<ImageView, String> =
        Collections.synchronizedMap(WeakHashMap())
    private var poolExecutor = Executors.newSingleThreadExecutor()
    private var mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())

    fun push(imageToLoad: ImageToLoad) {
        savedImagesWithUrls[imageToLoad.imageView] = imageToLoad.url
        checkIfShouldAddImageToQueue(imageToLoad)
    }

    private fun checkIfShouldAddImageToQueue(imageToLoad: ImageToLoad) {
        val bitmap: Bitmap? = memoryCache[imageToLoad.url]
        if (bitmap != null)
            imageToLoad.imageView.setImageBitmap(bitmap)
        else {
            tasksQueue.add(imageToLoad)
        }
    }

    fun execute() {
        checkIfPoolExecutorIsShutdown()
        if (tasksQueue.isEmpty()) {
            isExecuting = false
            return
        }
        if (isExecuting.not()) {
            isExecuting = true
            val task = tasksQueue.element()
            task?.let {
                startDownloadingImage(task)
            }
        }
    }

    private fun checkIfPoolExecutorIsShutdown() {
        if (poolExecutor.isShutdown) {
            poolExecutor = Executors.newSingleThreadExecutor()
        }
    }

    private fun startDownloadingImage(imageToLoad: ImageToLoad) {
        poolExecutor.execute {
            var urlConnection: HttpURLConnection? = null
            try {
                val uri = URL(imageToLoad.url)
                urlConnection = uri.openConnection() as HttpURLConnection
                val inputStream: InputStream? = urlConnection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                mainThreadHandler.post {
                    tasksQueue.poll()
                    isExecuting = false
                    loadImage(imageToLoad, bitmap)
                    execute()
                }
            } catch (e: Exception) {
                mainThreadHandler.post {
                    isExecuting = false
                    tasksQueue.add(imageToLoad)
                    execute()
                }
                urlConnection?.disconnect()
            } finally {
                urlConnection?.disconnect()
            }
        }
    }

    private fun loadImage(ImageToLoad: ImageToLoad, bitmap: Bitmap) {
        ImageToLoad.imageView.setImageBitmap(bitmap)
        memoryCache.put(ImageToLoad.url, bitmap)
    }

    fun releaseResources() {
        memoryCache.clear()
        tasksQueue.clear()
        savedImagesWithUrls.clear()
        poolExecutor.shutdownNow()
    }
}