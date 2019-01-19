package com.danpinciotti.mobile.hackernews.core.networking

import android.content.Context
import android.net.ConnectivityManager
import com.danpinciotti.mobile.hackernews.exceptions.NoNetworkConnectionException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class NoNetworkConnectionInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoNetworkConnectionException()
        }

        try {
            return chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            throw NoNetworkConnectionException()
        } catch (e: UnknownHostException) {
            throw NoNetworkConnectionException()
        }
    }

    private fun isConnected(): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return true

        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isAvailable
    }
}