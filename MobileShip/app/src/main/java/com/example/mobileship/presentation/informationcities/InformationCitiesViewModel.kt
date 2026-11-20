package com.example.mobileship.presentation.informationcities

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileship.domain.repository.CityInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class InformationCitiesViewModel @Inject constructor(
    private val repository: CityInfoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val cityId: String? = savedStateHandle["cityId"]

    private val _uiState = MutableStateFlow(InformationCitiesUiState())
    val uiState: StateFlow<InformationCitiesUiState> = _uiState.asStateFlow()

    init {
        loadCity()
    }

    private fun loadCity() {
        val id = cityId

        if (id == null) {
            _uiState.value = InformationCitiesUiState(
                error = "Не указан идентификатор города"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = InformationCitiesUiState(isLoading = true)

            repository.getCities()
                .catch { exception ->
                    _uiState.value = InformationCitiesUiState(
                        error = exception.message
                            ?: "Не удалось загрузить информацию о городе"
                    )
                }
                .collect { cities ->
                    val city = cities.find { it.id == id }

                    _uiState.value = InformationCitiesUiState(
                        city = city,
                        error = if (city == null) {
                            "Информация о городе не найдена"
                        } else {
                            null
                        }
                    )
                }
        }
    }
}