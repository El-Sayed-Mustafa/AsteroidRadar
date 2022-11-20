package com.udacity.asteroidradar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity(tableName = "asteroids")
data class AsteroidEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val codename: String,
    val absoluteMagnitude: Double,
    val closeApproachDate: String,
    val distanceFromEarth: Double,
    val relativeVelocity: Double,
    val estimatedDiameter: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<AsteroidEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            absoluteMagnitude = it.absoluteMagnitude,
            closeApproachDate = it.closeApproachDate,
            distanceFromEarth = it.distanceFromEarth,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun List<Asteroid>.asDatabaseModel(): Array<AsteroidEntity> {
    return map {
        AsteroidEntity(
            id = it.id,
            codename = it.codename,
            absoluteMagnitude = it.absoluteMagnitude,
            distanceFromEarth = it.distanceFromEarth,
            estimatedDiameter = it.estimatedDiameter,
            closeApproachDate = it.closeApproachDate,
            relativeVelocity = it.relativeVelocity,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}
