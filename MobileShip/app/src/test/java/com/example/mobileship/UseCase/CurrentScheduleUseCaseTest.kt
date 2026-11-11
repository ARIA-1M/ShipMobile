package com.example.mobileship.UseCase

import app.cash.turbine.test
import com.example.mobileship.domain.model.Schedule
import com.example.mobileship.domain.repository.ScheduleRepository
import com.example.mobileship.domain.usecase.CurrentScheduleUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrentScheduleUseCaseTest {

    private lateinit var scheduleRepository: ScheduleRepository
    private lateinit var useCase: CurrentScheduleUseCase

    @Before
    fun setup() {
        scheduleRepository = mockk()
        useCase = CurrentScheduleUseCase(scheduleRepository)
    }

    // Проверка: UseCase получает список мероприятий из репозитория
    @Test
    fun currentScheduleReturnsEventsList() = runTest {
        val events = listOf(
            Schedule("1", "Обед", "12:00", "13:00", "Ресторан", false)
        )
        every { scheduleRepository.getSchedule() } returns flowOf(events)

        useCase().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Обед", result[0].title)
            awaitComplete()
        }
    }
}