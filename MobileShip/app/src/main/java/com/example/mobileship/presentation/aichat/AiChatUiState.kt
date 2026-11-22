package com.example.mobileship.presentation.aichat

/**
 * Модель одного сообщения в чате с ИИ
 * @property id Уникальный идентификатор сообщения
 * @property text Текст сообщения
 * @property isFromUser Флаг указывающий отправителя (true - пользователь, false - ИИ)
 * @property time Время отправки сообщения
 */
data class AiChatMessage(
    val id: Long,
    val text: String,
    val isFromUser: Boolean,
    val time: String
)

/**
 * Состояние экрана чата с ИИ
 * Содержит историю сообщений, введенный текст, статус загрузки и ошибки
 * @property messages Список сообщений в чате
 * @property inputText Текст в поле ввода
 * @property isLoading Флаг загрузки (ожидание ответа от ИИ)
 * @property error Текст ошибки если она произошла
 */
data class AiChatUiState(
    val messages: List<AiChatMessage> = listOf(
        AiChatMessage(
            id = 1L,
            text = "Здравствуйте! Я ваш ИИ-консьерж. Чем могу помочь сегодня?",
            isFromUser = false,
            time = ""
        )
    ),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)