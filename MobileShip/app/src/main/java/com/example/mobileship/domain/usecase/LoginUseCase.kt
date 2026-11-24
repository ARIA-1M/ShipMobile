package com.example.mobileship.domain.usecase

import com.example.mobileship.data.local.dao.UserDao
import com.example.mobileship.data.local.entity.UserEntity
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.model.User
import javax.inject.Inject

/**
 * Use Case для авторизации пользователя по QR-коду
 * Разбирает строку QR-кода и сохраняет данные пользователя в локальную базу
 */
class LoginUseCase @Inject constructor(
    private val userDao: UserDao
) {

    /**
     * Выполняет вход в систему по отсканированному QR-коду
     * @param qrCode Строка с данными QR-кода (role=user; cabin=101)
     * @return DomainResult с данными пользователя или сообщением об ошибке
     */
    suspend operator fun invoke(qrCode: String): DomainResult<User> {
        if (qrCode.isBlank()) {
            return DomainResult.Error("QR-код не может быть пустым")
        }

        return try {
            val parts = qrCode.split(";").associate { part ->
                val keyValue = part.split("=", limit = 2)

                if (keyValue.size == 2) {
                    keyValue[0].trim().lowercase() to keyValue[1].trim()
                } else {
                    "" to ""
                }
            }

            val role = parts["role"]
                ?.lowercase()
                ?: return DomainResult.Error("Роль не найдена в QR-коде")

            if (role !in listOf("user", "admin", "manager")) {
                return DomainResult.Error("Неизвестная роль пользователя")
            }

            val cabinNumber = parts["cabin"]?.toIntOrNull()

            if (role == "user" && (cabinNumber == null || cabinNumber <= 0)) {
                return DomainResult.Error(
                    "Номер каюты не найден или указан неверно"
                )
            }

            val user = User(
                role = role,
                cabinNumber = cabinNumber
            )

            userDao.insertUser(
                UserEntity(
                    idUser = "1",
                    role = role,
                    cabinNumber = cabinNumber
                )
            )

            DomainResult.Success(user)
        } catch (e: Exception) {
            DomainResult.Error(
                "Ошибка обработки QR-кода: ${e.message}"
            )
        }
    }
}