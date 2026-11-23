
package com.example.mobileship.UseCase

import com.example.mobileship.domain.model.AiResponse
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.repository.AiRepository
import com.example.mobileship.domain.usecase.AskAiUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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

    // Проверка: пустой вопрос возвращает ошибку
    // без обращения к репозиторию
    @Test
    fun askAiWithEmptyQuestionReturnsError() = runTest {
        val useCase = AskAiUseCase(aiRepository)

        val result = useCase("", "Ярославль", "")

        assertTrue(result is DomainResult.Error)
        coVerify(exactly = 0) {
            aiRepository.askAssistant(any(), any(), any())
        }
    }

    // Проверка: вопрос, город и расписание передаются в репозиторий
    @Test
    fun askAiReturnsSuccess() = runTest {
        val expectedResponse = AiResponse("assistant", "Привет!")
        val question = "Как дела?"
        val city = "Ярославль"
        val schedule = "10:00-12:00: Экскурсия"

        coEvery {
            aiRepository.askAssistant(question, city, schedule)
        } returns DomainResult.Success(expectedResponse)

        val useCase = AskAiUseCase(aiRepository)
        val result = useCase(question, city, schedule)

        assertTrue(result is DomainResult.Success)
        assertEquals(
            expectedResponse,
            (result as DomainResult.Success).data
        )

        coVerify(exactly = 1) {
            aiRepository.askAssistant(question, city, schedule)
        }
    }
}
