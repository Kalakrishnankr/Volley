package com.beachpartnerllc.beachpartner.update

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 12 Jun 2018 at 9:15 AM
 */
class UpdateViewModel(private val app: Application) : AndroidViewModel(app) {
    val update = MutableLiveData<Resource<Update>>()
    private lateinit var apis: ApiService

    fun checkForUpdated(): LiveData<Resource<Update>> {
        update.value = Resource.loading()

        api().checkForUpdate(Update()).enqueue(object : Callback<Update?> {
            override fun onFailure(call: Call<Update?>?, t: Throwable?) {
                update.value = Resource.error()
            }

            override fun onResponse(call: Call<Update?>?, response: Response<Update?>) {
                if (response.code() == 200) {
                    update.value = Resource.success(response.body())
                }
            }
        })
        return update
    }

    fun updateFcmToken(token : String){
        api().updateFcmToken(hashMapOf("fcmToken" to token)).enqueue(object: Callback<FcmUpdate?> {
            override fun onFailure(call: Call<FcmUpdate?>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<FcmUpdate?>?, response: Response<FcmUpdate?>?) {

            }
        })
    }

    fun api(): ApiService {
        if (!::apis.isInitialized) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor(app))
                    .addInterceptor(logger)
                    .build()
            apis = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .baseUrl(ApiService.URL_BASE)
                    .client(client)
                    .build()
                    .create(ApiService::class.java)
        }

        return apis
    }
}