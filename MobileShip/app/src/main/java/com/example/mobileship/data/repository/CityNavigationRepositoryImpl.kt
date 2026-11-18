package com.example.mobileship.data.repository

import com.example.mobileship.data.local.dao.CityNavigateDao
import com.example.mobileship.data.local.entity.CityNavigateEntity
import com.example.mobileship.domain.model.CityNavigate
import com.example.mobileship.domain.repository.CityNavigationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Реализация репозитория навигации на основе локальной базы данных Room
 */
class CityNavigationRepositoryImpl @Inject constructor(
    private val dao: CityNavigateDao
) : CityNavigationRepository {

    /**
     * Получает маршрут из локальной базы данных
     * @return Flow со списком городов
     */
    override fun getRoute(): Flow<List<CityNavigate>> {
        return dao.getRoute().map { entities ->
            entities.map { entity ->
                CityNavigate(
                    id = entity.idCity,
                    name = entity.name,
                    condition = entity.condition
                )
            }
        }
    }

    /**
     * Обновляет состояние навигации (в текущей реализации не требуется)
     */
    override suspend fun updateNavigationState() {
        // В будущем здесь можно пересчитывать статусы городов
    }
}