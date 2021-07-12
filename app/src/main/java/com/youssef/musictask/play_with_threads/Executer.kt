package com.youssef.musictask.play_with_threads

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CountDownLatch

class Executer(
    private val tasks: List<Runnable>,
    private val callback: Callback?
) : Thread() {
    private val latch: CountDownLatch = CountDownLatch(tasks.size)
    private val workers: ConcurrentLinkedQueue<Worker> = ConcurrentLinkedQueue<Worker>()

    init {
        tasks.forEach {
            workers.add(Worker(it,latch))
        }
    }

    fun execute() {
        start()
    }

    override fun run() {
        while (true) {
            val worker = workers.poll() ?: break
            worker.start()
        }
        latch.await()
        callback?.isCompleted()
    }

    class Builder {
        private val mTasks = arrayListOf<Runnable>()
        private var mCallBack: Callback? = null

        fun add(task: Runnable): Builder {
            mTasks.add(task)
            return this
        }

        fun add(callback: Callback): Builder {
            mCallBack = callback
            return this
        }

        fun build(): Executer {
            return Executer(mTasks, mCallBack)
        }
    }


    interface Callback {
        fun isCompleted()
    }
}