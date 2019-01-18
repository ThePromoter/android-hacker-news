package com.danpinciotti.mobile.hackernews.core.networking

import com.danpinciotti.mobile.hackernews.exceptions.NoNetworkConnectionException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import timber.log.Timber


class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        Timber.d(
            "--> Sending request %s using method %s",
            request.url(), request.method()
        )
        Timber.v("%n%s", request.headers())

        if (!request.method().equals("GET")) {
            val requestBuffer = Buffer()
            if (request.body() != null) {
                request.body()?.writeTo(requestBuffer)
                Timber.d(requestBuffer.readUtf8())
            } else {
                Timber.d("No body")
            }
        }

        try {
            val response = chain.proceed(request)

            val t2 = System.nanoTime()
            Timber.d(
                "<-- Received response code %d for %s in %.1fms",
                response.code(), response.request().url(), (t2 - t1) / 1e6
            )
            Timber.v("%n%s", response.headers())

            val contentType = response.body()!!.contentType()
            val content = response.body()!!.string()
            Timber.d(content)

            val wrappedBody = ResponseBody.create(contentType, content)
            return response.newBuilder().body(wrappedBody).build()
        } catch (nnce: NoNetworkConnectionException) {
            Timber.e("<-- no network connection")
            throw nnce
        }

    }
}