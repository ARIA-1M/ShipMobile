
package com.example.mobileship.presentation.alarms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileship.domain.hardware.AlarmSoundManager
import com.example.mobileship.domain.model.CabinStatus
import com.example.mobileship.domain.repository.CabinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val cabinRepository: CabinRepository,
    private val alarmSoundManager: AlarmSoundManager
) : ViewModel() {

    private val _cabins = MutableStateFlow<List<CabinStatus>>(emptyList())
    val cabins: StateFlow<List<CabinStatus>> = _cabins.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ID тревог, звук которых пользователь уже принял
    private val acknowledgedAlarmIds = mutableSetOf<String>()

    // ID тревог из предыдущего обновления
    private var previousAlarmIds = emptySet<String>()

    init {
        loadAlarms()
    }

    private fun loadAlarms() {
        viewModelScope.launch {
            cabinRepository.getActiveAlarms().collect { alarms ->

                val currentAlarmIds = alarms.map { it.id }.toSet()

                // Убираем подтверждение у тревог, которые исчезли
                acknowledgedAlarmIds.retainAll(currentAlarmIds)

                // Определяем новые тревоги
                val newAlarmIds = currentAlarmIds - previousAlarmIds

                // Список всегда обновляется данными API без изменений
                _cabins.value = alarms
                _isLoading.value = false

                // Новая тревога снова включает звук.
                // При первом получении списка звук включается,
                // если в нём есть активные тревоги.
                if (newAlarmIds.isNotEmpty() ||
                    (previousAlarmIds.isEmpty() && currentAlarmIds.isNotEmpty())
                ) {
                    alarmSoundManager.startAlarm()
                }

                // Если тревог больше нет — останавливаем звук
                if (currentAlarmIds.isEmpty()) {
                    alarmSoundManager.stopAlarm()
                }

                previousAlarmIds = currentAlarmIds
            }
        }
    }

    // Принять сигнал: только выключить звук.
    // Список кают остаётся без изменений.
    fun acknowledgeAlarms() {
        acknowledgedAlarmIds.addAll(_cabins.value.map { it.id })
        alarmSoundManager.stopAlarm()
    }

    override fun onCleared() {
        alarmSoundManager.stopAlarm()
        super.onCleared()
    }
}
