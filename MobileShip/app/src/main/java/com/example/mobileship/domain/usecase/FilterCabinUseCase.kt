package com.example.mobileship.domain.usecase

import com.example.mobileship.domain.model.CabinStatus
import com.example.mobileship.domain.repository.CabinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilterCabinUseCase @Inject constructor(
    private val repository: CabinRepository
) {
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