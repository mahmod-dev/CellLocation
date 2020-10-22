package com.mahmouddev.celllocation.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private val BASE_URL = "https://ap1.unwiredlabs.com/v2/"

    private fun getRetrofit(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)


}