package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    /*  private val asteroidLiveData = MutableLiveData<List<Asteroid>>()
      val asteroids:LiveData<List<Asteroid>> = asteroidLiveData

      init {
          viewModelScope.launch {
              val asteroids =api.getAsteroids()
              delay(2000)
              asteroidLiveData.value = asteroids
          }
      }*/

    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    init {
            getAsteroidProperties()

    }

    private fun getAsteroidProperties() {
        AsteroidApi.retrofitService.getAsteroids(API_KEY).enqueue( object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val asteroids= response.body()
                val result = parseAsteroidsJsonResult(JSONObject(asteroids))
                _response.value = result.size.toString()
            }
        })
    }

}