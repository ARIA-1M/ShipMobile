package com.example.mobileship.domain.model

/**
 * Модель для сохранения данных пользователя
 */
data class User(
    val role: String,
    val cabinNumber: Int? = null
)
