package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.CityNavigate
import kotlinx.coroutines.flow.Flow

interface CityNavigationRepository {
    fun getRoute(): Flow<List<CityNavigate>>
    suspend fun updateNavigationState()
}