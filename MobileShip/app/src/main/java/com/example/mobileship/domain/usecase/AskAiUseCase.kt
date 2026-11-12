package com.example.mobileship.domain.usecase


import com.example.mobileship.domain.model.AiResponse
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.repository.AiRepository
import javax.inject.Inject

class AskAiUseCase @Inject constructor(
    private val repository: AiRepository
) {
    suspend operator fun invoke(question: String, currentCity: String): DomainResult<AiResponse> {
        if (question.isBlank()) {
            return DomainResult.Error("Вопрос не может быть пустым")
        }

        val prompt = "Контекст: мы находимся в городе $currentCity. Вопрос пользователя: $question"

        return repository.askAssistant(prompt)
    }
}