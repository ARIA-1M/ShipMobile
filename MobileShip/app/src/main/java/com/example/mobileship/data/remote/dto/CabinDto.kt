package com.example.mobileship.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * DTO-модель данных о каюте
 */
@Serializable
data class CabinDto(
    val idCabin: Int,
    val number: String,
    val statusName: String
)

/**
 * DTO-модель ответа сервера со списком кают
 * Используется для пагинации
 */
@Serializable
data class CabinResponseDto(
    val total: Int,
    val page: Int,
    val limit: Int,
    val data: List<CabinDto>
)