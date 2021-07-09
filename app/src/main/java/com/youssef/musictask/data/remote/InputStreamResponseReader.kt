package com.youssef.musictask.data.remote

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection

object InputStreamResponseReader {
    fun getResponse(httpURLConnection: HttpURLConnection): String {
        val content = StringBuilder()
        try {
            val inputStreamReader = InputStreamReader(httpURLConnection.inputStream)
            val bufferedReader =
                BufferedReader(InputStreamReader(httpURLConnection.inputStream))
            try {
                var line = bufferedReader.readLine()
                while (line != null) {
                    content.append(line)
                    line = bufferedReader.readLine()
                }
            } finally {
                inputStreamReader.close()
                bufferedReader.close()
            }
        } finally {
            httpURLConnection.disconnect()
        }
        return content.toString()
    }
}