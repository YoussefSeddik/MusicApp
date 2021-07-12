package com.youssef.musictask.utils.image_loader_queue

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.core.os.HandlerCompat
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ImageLoader {
    private var isExecuting = false
    private var memoryCache: MemoryCache = MemoryCache()
    private val tasksQueue: Queue<ImageToLoad> = LinkedList()
    private var poolExecutor: ExecutorService? = null
    private var mainThreadHandler: Handler? = null
    private var imagesWantsToBeLoadedMap = WeakHashMap<ImageView, Long>()
    private var images = mutableListOf<Long>()
    private var mUrl: String = ""

    init {
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        poolExecutor = Executors.newSingleThreadExecutor()
    }

    fun load(url: String): ImageLoader {
        mUrl = url
        return this
    }

    fun into(imageView: ImageView) {
        val currentTime = Calendar.getInstance().timeInMillis
        val imageToLoad = ImageToLoad(imageView, mUrl,currentTime)
        checkIfShouldAddImageToLoaderQueue(imageToLoad)
    }

    private fun checkIfShouldAddImageToLoaderQueue(imageToLoad: ImageToLoad) {
        val bitmap: Bitmap? = memoryCache[imageToLoad.url]
        if (bitmap != null)
            imageToLoad.imageView.setImageBitmap(bitmap)
        else {
            imagesWantsToBeLoadedMap[imageToLoad.imageView] = imageToLoad.createdAt
            tasksQueue.add(imageToLoad)
            execute()
        }
    }

    private fun execute() {
        checkIfResourcesExists()
        if (tasksQueue.isEmpty()) {
            isExecuting = false
            return
        }
        if (isExecuting.not()) {
            isExecuting = true
            val task = tasksQueue.element()
            task?.let {
                if (imageViewNoLongerAvailable(it)) execute()
                startDownloadingImage(task)
            }
        }
    }

    private fun imageViewNoLongerAvailable(imageToLoad: ImageToLoad): Boolean {
        val tag: Long? = imagesWantsToBeLoadedMap[imageToLoad.imageView]
        return tag == null || tag != imageToLoad.createdAt
    }


    private fun checkIfResourcesExists() {
        if (poolExecutor == null || poolExecutor?.isShutdown == true) {
            poolExecutor = Executors.newSingleThreadExecutor()
        }
        if (mainThreadHandler == null) {
            mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        }
    }

    private fun startDownloadingImage(imageToLoad: ImageToLoad) {
        poolExecutor?.execute {
            var urlConnection: HttpURLConnection? = null
            try {
                val uri = URL(imageToLoad.url)
                urlConnection = uri.openConnection() as HttpURLConnection
                val inputStream: InputStream? = urlConnection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                mainThreadHandler?.post {
                    tasksQueue.poll()
                    isExecuting = false
                    loadImage(imageToLoad, bitmap)
                    execute()
                }
            } catch (e: Exception) {
                mainThreadHandler?.post {
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

    private fun loadImage(imageToLoad: ImageToLoad, bitmap: Bitmap) {
        if (imageViewNoLongerAvailable(imageToLoad)) return
        imageToLoad.imageView.setImageBitmap(bitmap)
        memoryCache.put(imageToLoad.url, bitmap)
    }

    fun releaseResources() {
        memoryCache.clear()
        tasksQueue.clear()
        poolExecutor?.shutdownNow()
        poolExecutor = null
        mainThreadHandler = null
    }
}