package com.example.mobileship.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(val password: String)

@Serializable
data class LoginResponseDto
(
    val token: String,
    val role: String
)