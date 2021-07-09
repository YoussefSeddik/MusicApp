package com.youssef.musictask.data.remote

import java.net.HttpURLConnection
import java.net.URL


class HttpConnectionHelper() {
    private val allConnections: MutableList<HttpURLConnection> = mutableListOf()

    fun getTokenHttpConnection(): HttpURLConnection {
        val url = URL(BASE_URL + GET_TOKEN_PATH)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.setRequestProperty(X_MM_GATEWAY_KEY, X_MM_GATEWAY_VALUE)
        httpURLConnection.requestMethod = "POST"
        httpURLConnection.connect()
        allConnections.add(httpURLConnection)
        return httpURLConnection
    }

    fun getSongsHttpConnection(accessToken: String, searchText: String): HttpURLConnection {
        val queryParam = "?$QUERY=$searchText"
        val getMusicUrl = "$BASE_URL$GET_SONGS_PATH$queryParam"

        val url = URL(getMusicUrl)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.setRequestProperty(X_MM_GATEWAY_KEY, X_MM_GATEWAY_VALUE)
        httpURLConnection.setRequestProperty(ACCESS_TOKEN_KEY, ACCESS_TOKEN_VALUE + accessToken)
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.connect()
        allConnections.add(httpURLConnection)
        return httpURLConnection
    }

    fun releaseAllConnections() {
        allConnections.map {
            it.disconnect()
        }
        allConnections.clear()
    }

    companion object {
        const val BASE_URL = "http://staging-gateway.mondiamedia.com/"
        const val GET_TOKEN_PATH = "v0/api/gateway/token/client"
        const val GET_SONGS_PATH = "v2/api/sayt/flat"
        const val QUERY = "query"
        const val LIMIT = "limit"
        const val X_MM_GATEWAY_KEY = "X-MM-GATEWAY-KEY"
        const val X_MM_GATEWAY_VALUE = "Ge6c853cf-5593-a196-efdb-e3fd7b881eca"
        const val ACCESS_TOKEN_KEY = "Authorization"
        const val ACCESS_TOKEN_VALUE = "Bearer "
    }
}