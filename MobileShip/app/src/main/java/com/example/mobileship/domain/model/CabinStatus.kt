package com.example.mobileship.domain.model

/**
 * Модель для отображения статусов кают
 */
data class CabinStatus(
    val id: String,
    val cabinNumber: Int,
    val deck: Int,
    val status: String
)