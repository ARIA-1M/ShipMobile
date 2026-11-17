package com.example.mobileship.domain.usecase

import com.example.mobileship.domain.model.CityNavigate
import com.example.mobileship.domain.repository.CityNavigationRepository
import com.example.mobileship.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * Use Case для формирования маршрута с актуальными статусами городов
 * Определяет какие города пройдены какой текущий а какие еще впереди
 */
class RouteUseCase @Inject constructor(
    private val navigationRepository: CityNavigationRepository,
    private val scheduleRepository: ScheduleRepository
) {

    /**
     * Формирует список точек маршрута с расставленными статусами
     * @return Flow со списком городов где каждый имеет статус passed current или upcoming
     */
    operator fun invoke(): Flow<List<CityNavigate>> {
        return combine(
            navigationRepository.getRoute(),
            scheduleRepository.getSchedule()
        ) { route, schedule ->
            val pierEvent = schedule.find { it.location.contains("Причал", ignoreCase = true) }
            val currentCityName = if (pierEvent != null) {
                route.find { city -> pierEvent.title.contains(city.name, ignoreCase = true) }?.name
            } else null

            var isPassedCurrent = false

            route.map { city ->
                val condition = when {
                    city.name == currentCityName -> {
                        isPassedCurrent = true
                        "current"
                    }
                    !isPassedCurrent -> "passed"
                    else -> "upcoming"
                }
                city.copy(condition = condition)
            }
        }
    }
}