package com.udacity.asteroidradar.data

data class Asteroid(
    val id: Long,
    val distanceFromEarth: Double,
    val relativeVelocity: Double,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val codename: String,
    val isPotentiallyHazardous: Boolean
)