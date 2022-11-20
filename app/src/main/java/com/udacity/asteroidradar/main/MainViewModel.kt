package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepo
import kotlinx.coroutines.launch
import org.json.JSONObject


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    private var _properties = MutableLiveData<List<Asteroid>>()
    var lisAsteroid = ArrayList<Asteroid>()

    val properties: LiveData<List<Asteroid>>
        get() = _properties

    private val db = getDatabase(application)
    private val asteroidRepository = AsteroidRepo(db)

    init {
            getAsteroidProperties()

        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
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


    class Factory(private val app: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }

}