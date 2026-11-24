package com.example.mobileship.UseCase

import com.example.mobileship.data.local.dao.UserDao
import com.example.mobileship.data.local.entity.UserEntity
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.model.User
import com.example.mobileship.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var userDao: UserDao
    private lateinit var useCase: LoginUseCase

    @Before
    fun setup() {
        userDao = mockk()
        useCase = LoginUseCase(userDao)
    }

    // Проверка: Если QR-код пустой, возвращается ошибка
    @Test
    fun loginWithEmptyQrReturnsError() = runTest {
        val result = useCase("")

        assertTrue(result is DomainResult.Error)
        assertEquals(
            "QR-код не может быть пустым",
            (result as DomainResult.Error).message
        )

        coVerify(exactly = 0) {
            userDao.insertUser(any())
        }
    }

    // Проверка: Если QR-код верный, возвращается успех и данные пользователя
    @Test
    fun loginWithValidQrReturnsSuccess() = runTest {
        val expectedUser = User(
            role = "user",
            cabinNumber = 304
        )

        coEvery {
            userDao.insertUser(any())
        } just Runs

        val result = useCase("role=user; cabin=304")

        assertTrue(result is DomainResult.Success)
        assertEquals(
            expectedUser,
            (result as DomainResult.Success).data
        )

        coVerify(exactly = 1) {
            userDao.insertUser(
                UserEntity(
                    idUser = "1",
                    role = "user",
                    cabinNumber = 304
                )
            )
        }
    }

    // Проверка: Если роль неизвестна, возвращается ошибка
    @Test
    fun loginWithUnknownRoleReturnsError() = runTest {
        val result = useCase("role=guest; cabin=304")

        assertTrue(result is DomainResult.Error)
        assertEquals(
            "Неизвестная роль пользователя",
            (result as DomainResult.Error).message
        )
    }

    // Проверка: Если у пассажира отсутствует номер каюты, возвращается ошибка
    @Test
    fun loginWithoutCabinNumberReturnsError() = runTest {
        val result = useCase("role=user")

        assertTrue(result is DomainResult.Error)
        assertEquals(
            "Номер каюты не найден или указан неверно",
            (result as DomainResult.Error).message
        )
    }
}
