package com.example.mobileship.domain.model

/**
 * Модель для отображения информации о городе
 */
data class CityInfo(
    val id: String,
    val name: String,
    val description: String,
    val population: Int,
    val yearFoundation: Int,
    val imageUrl: String
)