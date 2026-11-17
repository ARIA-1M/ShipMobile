package com.example.mobileship.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

/**
 * Локальная сущность для точек маршрута (навигация)
 */
@Entity(tableName = "city_navigate")
data class CityNavigateEntity(
    @PrimaryKey val idCity: String,
    val name: String,
    val condition: String
)