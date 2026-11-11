package com.example.mobileship.UseCase

import app.cash.turbine.test
import com.example.mobileship.domain.model.CityNavigate
import com.example.mobileship.domain.model.Schedule
import com.example.mobileship.domain.repository.CityNavigationRepository
import com.example.mobileship.domain.repository.ScheduleRepository
import com.example.mobileship.domain.usecase.CurrentPositionUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrentPositionUseCaseTest {

    private lateinit var scheduleRepository: ScheduleRepository
    private lateinit var navigationRepository: CityNavigationRepository

    @Before
    fun setup() {
        scheduleRepository = mockk()
        navigationRepository = mockk()
    }

    // Проверка: UseCase определяет текущий город на основе события "Причал" в расписании
    @Test
    fun currentPositionReturnsCorrectCity() = runTest {
        val route = listOf(
            CityNavigate("1", "Нижний Новгород", "passed"),
            CityNavigate("2", "Кострома", "current"),
            CityNavigate("3", "Ярославль", "upcoming")
        )
        val schedule = listOf(
            Schedule("1", "Причал Кострома", "10:00", "12:00", "Причал", false)
        )

        every { navigationRepository.getRoute() } returns flowOf(route)
        every { scheduleRepository.getSchedule() } returns flowOf(schedule)

        val useCase = CurrentPositionUseCase(scheduleRepository, navigationRepository)

        useCase().test {
            val result = awaitItem()
            assertEquals("Кострома", result.name)
            assertEquals("current", result.condition)
            awaitComplete()
        }
    }
}