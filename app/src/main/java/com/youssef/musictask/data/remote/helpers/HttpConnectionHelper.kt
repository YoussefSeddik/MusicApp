package com.youssef.musictask.data.remote.helpers

import java.net.HttpURLConnection
import java.net.URL


class HttpConnectionHelper() {
    fun getTokenHttpConnection(): HttpURLConnection {
        val url = URL(BASE_URL + GET_TOKEN_PATH)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.setRequestProperty(X_MM_GATEWAY_KEY, X_MM_GATEWAY_VALUE)
        httpURLConnection.requestMethod = RequestType.POST.name
        httpURLConnection.connect()
        return httpURLConnection
    }

    fun getSongsHttpConnection(searchText: String, accessToken: String): HttpURLConnection {
        val queryParam = "?$QUERY=$searchText"
        val getMusicUrl = "$BASE_URL$GET_SONGS_PATH$queryParam&limit=20&includeArtists=false&filterByStreamingOnly=true"

        val url = URL(getMusicUrl)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.setRequestProperty(X_MM_GATEWAY_KEY, X_MM_GATEWAY_VALUE)
        httpURLConnection.setRequestProperty(ACCESS_TOKEN_KEY, ACCESS_TOKEN_VALUE + accessToken)
        httpURLConnection.requestMethod = RequestType.GET.name
        httpURLConnection.connect()
        return httpURLConnection
    }

    companion object {
        const val BASE_URL = "http://staging-gateway.mondiamedia.com/"
        const val GET_TOKEN_PATH = "v0/api/gateway/token/client"
        const val GET_SONGS_PATH = "v2/api/sayt/flat"
        const val QUERY = "query"
        const val X_MM_GATEWAY_KEY = "X-MM-GATEWAY-KEY"
        const val X_MM_GATEWAY_VALUE = "G2269608a-bf41-2dc7-cfea-856957fcab1e"
        const val ACCESS_TOKEN_KEY = "Authorization"
        const val ACCESS_TOKEN_VALUE = "Bearer "
    }

    enum class RequestType {
        POST,
        GET
    }
}