package com.example.mobileship.presentation.alarms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileship.domain.model.CabinStatus
import com.example.mobileship.domain.repository.CabinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана тревог
 * Загружает список кают с активными сигналами SOS
 */
@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val cabinRepository: CabinRepository
) : ViewModel() {

    private val _cabins = MutableStateFlow<List<CabinStatus>>(emptyList())

    /**
     * Публичный поток со списком кают с тревогами
     */
    val cabins: StateFlow<List<CabinStatus>> = _cabins.asStateFlow()

    private val _isLoading = MutableStateFlow(true)

    /**
     * Публичный поток статуса загрузки
     */
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadAlarms()
    }

    /**
     * Подписывается на поток тревог из репозитория
     * Обновляет список кают и статус загрузки
     */
    private fun loadAlarms() {
        viewModelScope.launch {
            cabinRepository.getActiveAlarms().collect { alarms ->
                _cabins.value = alarms
                _isLoading.value = false
            }
        }
    }
}