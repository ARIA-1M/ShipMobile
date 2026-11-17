package com.example.mobileship.domain.usecase

import com.example.mobileship.domain.model.AiResponse
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.repository.AiRepository
import javax.inject.Inject

/**
 * Use Case для отправки вопроса ИИ-ассистенту
 * Формирует контекст и проверяет валидность входных данных
 */
class AskAiUseCase @Inject constructor(
    private val repository: AiRepository
) {

    /**
     * Отправляет вопрос ассистенту с учетом текущего города
     * @param question Текст вопроса от пользователя
     * @param currentCity Название текущего города для контекста
     * @return DomainResult с ответом ИИ или сообщением об ошибке
     */
    suspend operator fun invoke(question: String, currentCity: String): DomainResult<AiResponse> {
        if (question.isBlank()) {
            return DomainResult.Error("Вопрос не может быть пустым")
        }

        val prompt = "Контекст: мы находимся в городе $currentCity. Вопрос пользователя: $question"

        return repository.askAssistant(prompt)
    }
}