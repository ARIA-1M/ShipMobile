package com.example.mobileship.data.repository

import com.example.mobileship.data.local.dao.CityNavigateDao
import com.example.mobileship.data.local.entity.CityNavigateEntity
import com.example.mobileship.domain.model.CityNavigate
import com.example.mobileship.domain.repository.CityNavigationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CityNavigationRepositoryImpl @Inject constructor(
    private val dao: CityNavigateDao
) : CityNavigationRepository {

    override fun getRoute(): Flow<List<CityNavigate>> {
        return dao.getRoute()
            .onStart {
                if (dao.getRoute().first().isEmpty()) {
                    dao.insertRoute(
                        listOf(
                            CityNavigateEntity("1", "Нижний Новгород", "upcoming"),
                            CityNavigateEntity("2", "Кострома", "upcoming"),
                            CityNavigateEntity("3", "Ярославль", "upcoming"),
                            CityNavigateEntity("4", "Углич", "upcoming"),
                            CityNavigateEntity("5", "Москва", "upcoming"),
                            CityNavigateEntity("6", "Константиново", "upcoming"),
                            CityNavigateEntity("7", "Рязань", "upcoming"),
                            CityNavigateEntity("8", "Касимов", "upcoming"),
                            CityNavigateEntity("9", "Муром", "upcoming"),
                            CityNavigateEntity("10", "Павлово", "upcoming"),
                            CityNavigateEntity("11", "Нижний Новгород", "upcoming")
                        )
                    )
                }
            }
            .map { entities ->
                entities.map { entity ->
                    CityNavigate(
                        id = entity.idCity,
                        name = entity.name,
                        condition = entity.condition
                    )
                }
            }
    }

    override suspend fun updateNavigationState() {
        // Пока обновление статусов выполняется в RouteUseCase
    }
}
