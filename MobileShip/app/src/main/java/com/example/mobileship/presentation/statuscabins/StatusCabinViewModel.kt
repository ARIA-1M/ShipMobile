package com.example.mobileship.presentation.statuscabins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileship.domain.repository.CabinRepository
import com.example.mobileship.domain.usecase.FilterCabinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel для экрана статусов кают.
 *
 * Отвечает за загрузку кают, фильтрацию по палубам
 * и отображение кают с активными сигналами тревоги.
 */
@HiltViewModel
class StatusCabinViewModel @Inject constructor(
    private val filterCabinUseCase: FilterCabinUseCase,
    private val cabinRepository: CabinRepository
) : ViewModel() {

    private val selectedDeck = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<StatusCabinUiState> =
        selectedDeck
            .flatMapLatest { deck ->
                combine(
                    filterCabinUseCase(deck),
                    cabinRepository.getActiveAlarms()
                ) { cabins, alarms ->

                    val cleaningCabins = cabins.filterNot {
                        isSos(it.status)
                    }

                    val result = (cleaningCabins + alarms)
                        .distinctBy { it.id }

                    StatusCabinUiState(
                        cabins = result.sortedBy { it.cabinNumber },
                        selectedDeck = deck,
                        isLoading = false
                    )
                }
            }
            .onStart {
                emit(StatusCabinUiState(isLoading = true))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatusCabinUiState()
            )

    fun selectDeck(deck: Int?) {
        selectedDeck.value = deck
    }

    private fun isSos(status: String): Boolean {
        val value = status.trim().lowercase()
        return value.contains("sos") || value.contains("тревог")
    }
}
