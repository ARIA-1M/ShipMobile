package com.example.mobileship.data.local.cabin

import com.example.mobileship.data.remote.dto.CabinDto
import com.example.mobileship.domain.model.CabinStatus

fun CabinDto.toDomain(): CabinStatus {
    return CabinStatus(
        id = idCabin.toString(),
        cabinNumber = number.toIntOrNull() ?: 0,
        deck = number.firstOrNull()?.digitToIntOrNull() ?: 1,
        status = statusName
    )
}