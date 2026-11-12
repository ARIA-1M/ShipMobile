package com.example.mobileship.domain.usecase

import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.model.User
import com.example.mobileship.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(qrCode: String): DomainResult<User> {
        if (qrCode.isBlank()) {
            return DomainResult.Error("QR-код не может быть пустым")
        }
        return repository.loginByQr(qrCode)
    }
}