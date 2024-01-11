package com.gregorian_hijri.data.network

import com.google.gson.GsonBuilder
import com.gregorian_hijri.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client : OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()


     fun getInstance(): ApiServices {
        val retrofit: Retrofit =  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            //.addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)
            .build()
        return retrofit.create(ApiServices::class.java)
    }
    private fun createGson() = GsonBuilder().setLenient().create()

}
