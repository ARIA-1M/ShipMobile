package com.example.mobileship.data.local.airesponse

import com.example.mobileship.data.remote.ShipApi
import com.example.mobileship.data.remote.dto.AiRequestDto
import com.example.mobileship.data.remote.dto.ContextDto
import com.example.mobileship.data.remote.dto.MessageDto
import com.example.mobileship.data.remote.safeApiCall
import com.example.mobileship.domain.model.AiResponse
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.repository.AiRepository
import javax.inject.Inject

/**
 * Реализация репозитория для работы с искусственным интеллектом
 * Отправляет запросы к нейросети через сервер и возвращает ответы
 */
class AiRepositoryImpl @Inject constructor(
    private val api: ShipApi
) : AiRepository {

    override suspend fun askAssistant(
        prompt: String,
        city: String,
        schedule: String
    ): DomainResult<AiResponse> {

        val request = AiRequestDto(
            messages = listOf(
                MessageDto(
                    role = "user",
                    content = prompt
                )
            ),
            context = ContextDto(
                city = city,
                schedule = schedule
            )
        )

        val result = safeApiCall {
            api.askAi(request)
        }

        return when (result) {
            is DomainResult.Success ->
                DomainResult.Success(result.data.toDomain())

            is DomainResult.Error ->
                DomainResult.Error(result.message)
        }
    }
}