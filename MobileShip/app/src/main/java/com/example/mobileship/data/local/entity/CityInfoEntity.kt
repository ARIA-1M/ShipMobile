package com.example.mobileship.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

/**
 * Локальная сущность с информацией о городах (карточки городов)
 */
@Entity(tableName = "city_info")
data class CityInfoEntity(
    @PrimaryKey val idCity: String,
    val name: String,
    val population: Int,
    val yearFoundation: Int,
    val description: String,
    val imageUrl: String
)