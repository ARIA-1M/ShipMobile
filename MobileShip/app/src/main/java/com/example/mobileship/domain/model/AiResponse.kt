package com.example.mobileship.domain.model

/**
 * Модель сообщения для чата с искусственным интеллектом
 */
data class AiResponse(
    val role: String,
    val content: String
)