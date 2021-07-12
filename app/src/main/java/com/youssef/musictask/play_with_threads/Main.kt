package com.youssef.musictask.play_with_threads

import java.util.concurrent.atomic.AtomicBoolean

fun main() {
    val isProcessing = AtomicBoolean(true)
    Executer.Builder()
        .add {
            println("TASK 1 STARTED")
            try {
                Thread.sleep(1000)
            } catch (e: Exception) {

            }
            println("TASK 1 COMPLETED")
        }.add {
            println("TASK 2 STARTED")
            try {
                Thread.sleep(1000)
            } catch (e: Exception) {

            }
            println("TASK 2 COMPLETED")
        }.add {
            println("TASK 3 STARTED")
            try {
                Thread.sleep(1000)
            } catch (e: Exception) {

            }
            println("TASK 3 COMPLETED")
        }
        .add(object : Executer.Callback {
            override fun isCompleted() {
                isProcessing.set(false)
            }
        })
        .build()
        .execute()
}