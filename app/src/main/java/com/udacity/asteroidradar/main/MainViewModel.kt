package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.FilterAsteroid
import com.udacity.asteroidradar.data.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepo
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    private var _properties = MutableLiveData<List<Asteroid>>()

    val properties: LiveData<List<Asteroid>>
        get() = _properties

    private val db = getDatabase(application)
    private val asteroidRepository = AsteroidRepo(db)


    private val _navigateToDetailAsteroid = MutableLiveData<Asteroid>()
    val navigateToDetailAsteroid: LiveData<Asteroid>
        get() = _navigateToDetailAsteroid

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    private var _filterAsteroid = MutableLiveData(FilterAsteroid.ALL)

    @RequiresApi(Build.VERSION_CODES.O)
    val asteroidList = Transformations.switchMap(_filterAsteroid) {
        when (it!!) {
            FilterAsteroid.WEEK -> asteroidRepository.weekAsteroids
            FilterAsteroid.TODAY -> asteroidRepository.todayAsteroids
            else -> asteroidRepository.asteroids
        }
    }

    fun onClicked(asteroid: Asteroid) {
        _navigateToDetailAsteroid.value = asteroid
    }


    fun navigateToDetail() {
        _navigateToDetailAsteroid.value = null
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