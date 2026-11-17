package com.example.mobileship.domain.usecase

import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.model.User
import com.example.mobileship.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use Case для авторизации пользователя по QR-коду
 * Проверяет входные данные и вызывает репозиторий для получения профиля
 */
class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    /**
     * Выполняет вход в систему по отсканированному QR-коду
     * @param qrCode Строка с данными QR-кода
     * @return DomainResult с данными пользователя или сообщением об ошибке
     */
    suspend operator fun invoke(qrCode: String): DomainResult<User> {
        if (qrCode.isBlank()) {
            return DomainResult.Error("QR-код не может быть пустым")
        }
        return repository.loginByQr(qrCode)
    }
}