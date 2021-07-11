package com.youssef.musictask.data.remote.helpers

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object InputStreamResponseHelper {
    fun parseInputStream(inputStream: InputStream?): String? {
        if (inputStream == null) return null
        val content = StringBuilder()
        var bufferedReader: BufferedReader? = null
        try {
            bufferedReader =
                BufferedReader(InputStreamReader(inputStream))
            var line = bufferedReader.readLine()
            while (line != null) {
                content.append(line)
                line = bufferedReader.readLine()
            }
        } catch (e: Exception) {
            bufferedReader?.close()
        } finally {
            bufferedReader?.close()
        }
        return content.toString()
    }

    fun Int.isRequestSuccess() = this in 200..299
}