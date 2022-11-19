package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.data.Asteroid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel (
    api:AsteroidApi
) : ViewModel() {

    private val asteroidLiveData = MutableLiveData<List<Asteroid>>()
    val asteroids:LiveData<List<Asteroid>> = asteroidLiveData

    init {
        viewModelScope.launch {
            val asteroids =api.getAsteroids()
            delay(2000)
            asteroidLiveData.value = asteroids
        }
    }

}