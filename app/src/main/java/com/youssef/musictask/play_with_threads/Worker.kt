package com.youssef.musictask.play_with_threads

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean

class Worker(private val task: Runnable, private val latch: CountDownLatch) : Runnable {
    private var thread: Thread = Thread()
    private var started = AtomicBoolean(false)

    fun start() {
        if (started.getAndSet(true).not()) {
            thread.start()
        }
    }


    override fun run() {
        task.run()
        latch.countDown()
    }
}