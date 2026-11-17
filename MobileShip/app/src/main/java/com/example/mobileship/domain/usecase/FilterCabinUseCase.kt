package com.example.mobileship.domain.usecase

import com.example.mobileship.domain.model.CabinStatus
import com.example.mobileship.domain.repository.CabinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use Case для фильтрации кают требующих уборки по номеру палубы
 * Позволяет получить список кают как для конкретной палубы так и для всех палуб
 */
class FilterCabinUseCase @Inject constructor(
    private val repository: CabinRepository
) {

    /**
     * Фильтрует список кают по указанной палубе
     * @param deck Номер палубы для фильтрации или null для получения всех кают
     * @return Flow со списком отфильтрованных статусов кают
     */
    operator fun invoke(deck: Int? = null): Flow<List<CabinStatus>> {
        return repository.getCabinForCleaning().map { allCabins ->
            if (deck == null) {
                allCabins
            } else {
                allCabins.filter { cabin -> cabin.deck == deck }
            }
        }
    }
}