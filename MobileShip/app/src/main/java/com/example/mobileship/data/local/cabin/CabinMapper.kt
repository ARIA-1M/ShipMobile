package com.example.mobileship.data.local.cabin

import com.example.mobileship.data.remote.dto.CabinDto
import com.example.mobileship.domain.model.CabinStatus

/**
 * Маппер для преобразования данных модели каюты
 */
fun CabinDto.toDomain(): CabinStatus {
    return CabinStatus(
        id = idCabin.toString(),
        cabinNumber = number ?: 0,
        deck = deck ?: 1,
        status = statusName
    )
}