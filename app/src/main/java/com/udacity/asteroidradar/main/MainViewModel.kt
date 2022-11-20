package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject


class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    private var _properties = MutableLiveData<List<Asteroid>>()
    var lisAsteroid = ArrayList<Asteroid>()

    val properties: LiveData<List<Asteroid>>
        get() = _properties


    init {
            getAsteroidProperties()

    }

    private fun getAsteroidProperties() {
        viewModelScope.launch {
            try {
                val listResult = AsteroidApi.retrofitService.getAsteroids(API_KEY)
                lisAsteroid = parseAsteroidsJsonResult(JSONObject(listResult))
                _properties.value = lisAsteroid
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

}