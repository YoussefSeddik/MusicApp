package com.youssef.musictask.utils

import android.os.CountDownTimer

/**
 * FuturePromise give me ability to prevent send query request while user is still typing
 * like debounce in rxJava
 * */
object FuturePromise {
    var countDownTimer: CountDownTimer? = null

    fun startPromiseAfter(millisInFuture: Long, promiseAction: () -> Unit) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(millisInFuture, millisInFuture) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                promiseAction()
                countDownTimer = null
            }
        }.start()
    }
}
