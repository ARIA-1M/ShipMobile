package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.model.User

interface AuthRepository {
    suspend fun loginByQr(qrCode: String): DomainResult<User>
}