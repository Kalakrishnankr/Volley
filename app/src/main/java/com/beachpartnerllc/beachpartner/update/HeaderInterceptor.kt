package com.beachpartnerllc.beachpartner.update

import android.app.Application
import com.beachpartnerllc.beachpartner.connections.PrefManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 30 Jan 2018 at 2:29 PM
 */
class HeaderInterceptor(private val app: Application) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()!!

        request = if (request.header(ApiService.NO_AUTH) == null) {
            request.newBuilder()
                    .addHeader(TOKEN_KEY, "$TOKEN_VALUE ${PrefManager(app).token}")
                    .build()
        } else {
            request.newBuilder()
                    .removeHeader(ApiService.NO_AUTH)
                    .build()
        }

        return chain.proceed(request)
    }

    private companion object {
        const val TOKEN_KEY = "Authorization"
        const val TOKEN_VALUE = "Bearer"
    }
}