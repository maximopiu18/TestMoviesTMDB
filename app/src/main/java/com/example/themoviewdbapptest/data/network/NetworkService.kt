package com.example.themoviewdbapptest.data.network

import com.example.themoviewdbapptest.data.api.MoviesService
import com.example.themoviewdbapptest.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


import javax.net.ssl.*

class NetworkService {
    companion object{
        fun getRetrofitService(): MoviesService {



            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${Constants.BEARER_AUTH}").build()
                    chain.proceed(request)
                }
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })

                .build()).build()

            return retrofit.create(MoviesService::class.java)
        }
    }

}