package com.example.mobileship.domain.repository

import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.model.User

/**
 * Интерфейс репозитория для авторизации пользователей
 */
interface AuthRepository {

    /**
     * Выполняет авторизацию пользователя по QR-коду
     * @param qrCode Строка с данными QR-кода
     * @return DomainResult с данными пользователя или сообщением об ошибке
     */
    suspend fun loginByQr(qrCode: String): DomainResult<User>
}