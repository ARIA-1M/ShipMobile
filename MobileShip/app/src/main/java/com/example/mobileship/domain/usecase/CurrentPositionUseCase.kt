package com.example.mobileship.domain.usecase

import com.example.mobileship.domain.model.CityNavigate
import com.example.mobileship.domain.repository.CityNavigationRepository
import com.example.mobileship.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CurrentPositionUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val navigationRepository: CityNavigationRepository
) {
    operator fun invoke(): Flow<CityNavigate> {
        return combine(
            scheduleRepository.getSchedule(),
            navigationRepository.getRoute()
        ) { schedule, route ->

            val pierEvent = schedule.find { it.location.contains("Причал", ignoreCase = true) }

            val currentCityName = if (pierEvent != null) {
                route.find { city -> pierEvent.title.contains(city.name, ignoreCase = true) }?.name
            } else null

            val currentCity = route.find { it.name == currentCityName }
                ?: route.firstOrNull()
                ?: CityNavigate("0", "Неизвестно", "upcoming")

            currentCity.copy(condition = "current")
        }
    }
}