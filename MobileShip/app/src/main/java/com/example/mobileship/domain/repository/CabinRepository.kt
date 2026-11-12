package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.CabinStatus
import kotlinx.coroutines.flow.Flow

interface CabinRepository {
    fun getCabinForCleaning(): Flow<List<CabinStatus>>
    fun getActiveAlarms(): Flow<List<CabinStatus>>
}