package com.example.mobileship.UseCase

import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.model.User
import com.example.mobileship.domain.repository.AuthRepository
import com.example.mobileship.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var useCase: LoginUseCase

    @Before
    fun setup() {
        authRepository = mockk()
        useCase = LoginUseCase(authRepository)
    }

    // Проверка: Если QR-код пустой, возвращается ошибка
    @Test
    fun loginWithEmptyQrReturnsError() = runTest {
        val result = useCase("")
        assertTrue(result is DomainResult.Error)
        assertEquals("QR-код не может быть пустым", (result as DomainResult.Error).message)
    }

    // Проверка: Если QR-код верный, возвращается успех и данные пользователя
    @Test
    fun loginWithValidQrReturnsSuccess() = runTest {
        val expectedUser = User(role = "PASSENGER", cabinNumber = 304)
        coEvery { authRepository.loginByQr("valid_qr") } returns DomainResult.Success(expectedUser)

        val result = useCase("valid_qr")

        assertTrue(result is DomainResult.Success)
        assertEquals(expectedUser, (result as DomainResult.Success).data)
    }
}