package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.data.Asteroid
import retrofit2.http.GET

interface AsteroidApi {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids():List<Asteroid>
}