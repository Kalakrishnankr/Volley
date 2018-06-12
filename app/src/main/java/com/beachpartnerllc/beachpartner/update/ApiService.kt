package com.beachpartnerllc.beachpartner.update

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 12 Jun 2018 at 9:25 AM
 */
interface ApiService {
    @POST("check-app-version")
    fun checkForUpdate(@Body update: Update): Call<Update>

    companion object {
        const val URL_BASE = "https://beachpartner.com/api/"
    }
}