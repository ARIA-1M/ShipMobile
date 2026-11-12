package com.example.mobileship.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CabinDto(
    val idCabin: Int,
    val number: String,
    val statusName: String
)

@Serializable
data class CabinResponseDto(
    val total: Int,
    val page: Int,
    val limit: Int,
    val data: List<CabinDto>
)