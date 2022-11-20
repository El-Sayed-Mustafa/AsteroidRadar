package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Picture
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.data.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    private var _properties = MutableLiveData<List<Asteroid>>()

    val properties: LiveData<List<Asteroid>>
        get() = _properties

    private val db = getDatabase(application)
    private val asteroidRepo = AsteroidRepo(db)


    private val _navigateToDetailAsteroid = MutableLiveData<Asteroid>()
    val navigateToDetailAsteroid: LiveData<Asteroid>
        get() = _navigateToDetailAsteroid

    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture>
        get() = _picture

    init {
        viewModelScope.launch {
            asteroidRepo.refreshAsteroids()
            refreshPictureOfDay()
        }
    }


    val list = asteroidRepo.asteroids


    fun onClicked(asteroid: Asteroid) {
        _navigateToDetailAsteroid.value = asteroid
    }


    fun navigateToDetail() {
        _navigateToDetailAsteroid.value = null
    }

    private suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                _picture.postValue(
                    AsteroidApi.retrofitService.getPicture(API_KEY)
                )
            } catch (err: Exception) {
                Log.e("refreshPictureOfDay", err.printStackTrace().toString())
            }
        }
    }


}