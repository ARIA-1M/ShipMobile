package com.example.mobileship.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * DTO-модель запроса к ИИ-ассистенту
 */
@Serializable
data class AiRequestDto(
    val messages: List<MessageDto>,
    val context: ContextDto
)

/**
 * DTO-модель одного сообщения в диалоге
 */
@Serializable
data class MessageDto(
    val role: String,
    val content: String
)

/**
 * DTO-модель контекста для ИИ
 */
@Serializable
data class ContextDto(
    val city: String,
    val schedule: String? = null
)

/**
 * DTO-модель ответа от ИИ
 */
@Serializable
data class AiResponseDto(
    val role: String,
    val content: String
)