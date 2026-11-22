package com.example.mobileship.presentation.aichat

data class AiChatMessage(
    val id: Long,
    val text: String,
    val isFromUser: Boolean,
    val time: String
)

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
