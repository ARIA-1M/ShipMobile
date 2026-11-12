package com.example.mobileship.data.local.airesponse

import com.example.mobileship.data.remote.dto.AiResponseDto
import com.example.mobileship.domain.model.AiResponse

fun AiResponseDto.toDomain(): AiResponse {
    return AiResponse(role, content)
}