package com.example.mobileship.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiRequestDto(
    val messages: List<MessageDto>,
    val context: ContextDto
)

@Serializable
data class MessageDto
(
    val role: String,
    val content: String
)

@Serializable
data class ContextDto
(
    val city: String,
    val schedule: String? = null
)

@Serializable
data class AiResponseDto
(
    val role: String,
    val content: String
)