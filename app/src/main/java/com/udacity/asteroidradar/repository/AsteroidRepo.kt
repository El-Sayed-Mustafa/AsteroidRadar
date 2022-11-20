package com.udacity.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.AsteroidApiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.AsteroidDB
import com.udacity.asteroidradar.data.asDatabaseModel
import com.udacity.asteroidradar.data.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AsteroidRepo(private val db: AsteroidDB) {


    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(db.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }


    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val listResult = AsteroidApi.retrofitService.getAsteroids(Constants.API_KEY)
                val listAsteroid = parseAsteroidsJsonResult(JSONObject(listResult))
                db.asteroidDao.insert(*listAsteroid.asDatabaseModel())
            } catch (err: Exception) {
                Log.e("Failed: AsteroidRepFile", err.message.toString())
            }
        }
    }
}