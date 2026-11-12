package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.AiResponse
import com.example.mobileship.domain.model.DomainResult

interface AiRepository {
    suspend fun askAssistant(prompt: String): DomainResult<AiResponse>
}