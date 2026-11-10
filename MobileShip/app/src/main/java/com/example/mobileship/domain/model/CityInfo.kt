package com.example.mobileship.domain.model

data class CityInfo(
    val id: String,
    val name: String,
    val description: String,
    val population: Int,
    val yearFoundation: Int,
    val imageUrl: String
)