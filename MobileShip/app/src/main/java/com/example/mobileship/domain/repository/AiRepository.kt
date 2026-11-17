package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.AiResponse
import com.example.mobileship.domain.model.DomainResult

/**
 * Интерфейс репозитория для работы с искусственным интеллектом
 */
interface AiRepository {

    /**
     * Отправляет вопрос пользователя ИИ-ассистенту
     * @param prompt Текст вопроса от пользователя
     * @return DomainResult с ответом ИИ или сообщением об ошибке
     */
    suspend fun askAssistant(prompt: String): DomainResult<AiResponse>
}