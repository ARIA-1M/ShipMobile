package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.CityInfo
import com.example.mobileship.domain.model.DomainResult
import kotlinx.coroutines.flow.Flow

interface CityInfoRepository {
    fun getCities(): Flow<List<CityInfo>>
    suspend fun getDetailsCities(cityId: String): DomainResult<CityInfo>
}