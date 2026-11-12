package com.example.mobileship.UseCase

import app.cash.turbine.test
import com.example.mobileship.domain.model.*
import com.example.mobileship.domain.repository.*
import com.example.mobileship.domain.usecase.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AskAiUseCaseTest {
    private lateinit var aiRepository: AiRepository

    @Before
    fun setup() {
        aiRepository = mockk()
    }

    // Проверка: Если вопрос пустой, возвращается ошибка без обращения к ИИ
    @Test
    fun askAiWithEmptyQuestionReturnsError() = runTest {
        val useCase = AskAiUseCase(aiRepository)
        val result = useCase("", "Ярославль")
        assertTrue(result is DomainResult.Error)
    }

    // Проверка: Если вопрос есть, ИИ возвращает ответ
    @Test
    fun askAiReturnsSuccess() = runTest {
        val expectedResponse = AiResponse("Привет!", false)
        coEvery { aiRepository.askAssistant(any()) } returns DomainResult.Success(expectedResponse)

        val useCase = AskAiUseCase(aiRepository)
        val result = useCase("Как дела?", "Ярославль")

        assertTrue(result is DomainResult.Success)
        assertEquals(expectedResponse, (result as DomainResult.Success).data)
    }

}