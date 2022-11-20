package com.udacity.asteroidradar.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query




@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg asteroid: AsteroidEntity)

    @Query("SELECT * FROM asteroids ORDER BY closeApproachDate DESC")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>


    @Query("SELECT * FROM asteroids WHERE closeApproachDate = :begin ORDER BY closeApproachDate DESC")
    fun getAsteroidsToday(begin: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate BETWEEN :begin AND :finish ORDER BY closeApproachDate DESC")
    fun getAsteroidsPeriod(begin: String, finish: String): LiveData<List<AsteroidEntity>>

}
