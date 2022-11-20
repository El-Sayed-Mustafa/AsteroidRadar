package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.Picture
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object AsteroidApi {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS).build()


    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi)).build()


    val retrofitService: AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }
}


interface AsteroidApiService {

    @GET("planetary/apod")
    suspend fun getPicture(@Query("api_key") api_key: String): Picture

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("api_key") api_key: String): String
}

