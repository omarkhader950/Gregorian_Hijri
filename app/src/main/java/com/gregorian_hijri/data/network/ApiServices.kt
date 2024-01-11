package com.gregorian_hijri.data.network

import com.gregorian_hijri.data.model.ConverterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface  ApiServices {


    @GET("gToH/{date}")
    suspend fun dateConverter(
        @Path("date") id: String,
    ): Response<ConverterResponse>

}