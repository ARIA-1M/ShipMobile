package com.example.mobileship.domain.usecase

import com.example.mobileship.domain.model.AiResponse
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.repository.AiRepository
import javax.inject.Inject

/**
 * Use Case для отправки вопроса ИИ-ассистенту.
 * Формирует контекст и проверяет валидность входных данных.
 */
class AskAiUseCase @Inject constructor(
    private val repository: AiRepository
) {
    /**
     * @param question Вопрос пользователя
     * @param currentCity Текущий город
     * @param currentSchedule Расписание текущего города
     */
    suspend operator fun invoke(
        question: String,
        currentCity: String,
        currentSchedule: String
    ): DomainResult<AiResponse> {
        if (question.isBlank()) {
            return DomainResult.Error("Вопрос не может быть пустым")
        }

        return repository.askAssistant(
            prompt = question,
            city = currentCity,
            schedule = currentSchedule
        )
    }
}