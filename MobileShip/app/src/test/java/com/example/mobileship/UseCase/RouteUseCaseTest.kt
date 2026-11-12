package com.example.mobileship.UseCase

import app.cash.turbine.test
import com.example.mobileship.domain.model.CityNavigate
import com.example.mobileship.domain.model.Schedule
import com.example.mobileship.domain.repository.CityNavigationRepository
import com.example.mobileship.domain.repository.ScheduleRepository
import com.example.mobileship.domain.usecase.RouteUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RouteUseCaseTest {

    private lateinit var navigationRepository: CityNavigationRepository
    private lateinit var scheduleRepository: ScheduleRepository
    private lateinit var useCase: RouteUseCase

    @Before
    fun setup() {
        navigationRepository = mockk()
        scheduleRepository = mockk()
        useCase = RouteUseCase(navigationRepository, scheduleRepository)
    }

    // Проверка: UseCase правильно определяет пройденные, текущий и будущие города на основе расписания
    @Test
    fun routeSetsCorrectStatuses() = runTest {
        val route = listOf(
            CityNavigate("1", "Нижний Новгород", "passed"),
            CityNavigate("2", "Кострома", "passed"),
            CityNavigate("3", "Ярославль", "passed"),
            CityNavigate("4", "Москва", "passed")
        )
        val schedule = listOf(
            Schedule("1", "Причал Ярославль", "10:00", "12:00", "Причал", false)
        )

        every { navigationRepository.getRoute() } returns flowOf(route)
        every { scheduleRepository.getSchedule() } returns flowOf(schedule)

        useCase().test {
            val result = awaitItem()
            assertEquals("passed", result[0].condition) // НН
            assertEquals("passed", result[1].condition) // Кострома
            assertEquals("current", result[2].condition) // Ярославль
            assertEquals("upcoming", result[3].condition) // Москва
            awaitComplete()
        }
    }
}