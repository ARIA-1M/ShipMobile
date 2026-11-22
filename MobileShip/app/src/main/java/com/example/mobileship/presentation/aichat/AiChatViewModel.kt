package com.example.mobileship.presentation.aichat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileship.data.local.dao.AiChatDao
import com.example.mobileship.data.local.entity.AiChatEntity
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.repository.ScheduleRepository
import com.example.mobileship.domain.usecase.AskAiUseCase
import com.example.mobileship.domain.usecase.RouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel для экрана чата с ИИ
 * Управляет историей сообщений и отправляет запросы к нейросети
 */
@HiltViewModel
class AiChatViewModel @Inject constructor(
    private val askAiUseCase: AskAiUseCase,
    private val routeUseCase: RouteUseCase,
    private val scheduleRepository: ScheduleRepository,
    private val aiChatDao: AiChatDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiChatUiState())
    val uiState: StateFlow<AiChatUiState> = _uiState.asStateFlow()

    private var messageId = 1L

    init {
        loadChatHistory()
    }

    /**
     * Загружает историю сообщений из базы данных
     * Если история пуста добавляет приветственное сообщение
     */
    private fun loadChatHistory() {
        viewModelScope.launch {
            val history = aiChatDao.getChatHistory().first()

            if (history.isEmpty()) {
                aiChatDao.insertMessage(
                    AiChatEntity(
                        message = "Здравствуйте! Я ваш ИИ-консьерж. Чем могу помочь сегодня?",
                        role = "assistant"
                    )
                )
            }

            aiChatDao.getChatHistory().collect { entities ->
                val messages = entities.map { entity ->
                    AiChatMessage(
                        id = entity.idMessage.toLong(),
                        text = entity.message,
                        isFromUser = entity.role == "user",
                        time = ""
                    )
                }

                messageId = (entities.maxOfOrNull { it.idMessage } ?: 0).toLong()

                _uiState.update {
                    it.copy(messages = messages)
                }
            }
        }
    }

    /**
     * Обновляет текст в поле ввода
     * @param value Новый текст
     */
    fun onInputChange(value: String) {
        _uiState.update {
            it.copy(inputText = value, error = null)
        }
    }

    /**
     * Отправляет сообщение пользователя в ИИ
     * Собирает контекст (город и расписание) и сохраняет ответ
     * @param text Текст сообщения
     */
    fun sendMessage(text: String = _uiState.value.inputText) {
        val question = text.trim()

        if (question.isBlank() || _uiState.value.isLoading) return

        _uiState.update {
            it.copy(
                inputText = "",
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                aiChatDao.insertMessage(
                    AiChatEntity(
                        message = question,
                        role = "user"
                    )
                )

                val route = routeUseCase().first()
                val currentCity = route.firstOrNull {
                    it.condition == "current"
                }?.name

                if (currentCity == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Не удалось определить текущий город"
                        )
                    }
                    return@launch
                }

                val schedule = scheduleRepository.getSchedule().first()

                val scheduleText = schedule.joinToString("\n") { event ->
                    "${event.startTime}-${event.endTime}: ${event.title}, место: ${event.location}"
                }

                when (
                    val result = askAiUseCase(
                        question,
                        currentCity,
                        scheduleText
                    )
                ) {
                    is DomainResult.Success -> {
                        aiChatDao.insertMessage(
                            AiChatEntity(
                                message = result.data.content,
                                role = "assistant"
                            )
                        )

                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is DomainResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Не удалось получить контекст"
                    )
                }
            }
        }
    }

    /**
     * Очищает историю чата и добавляет приветственное сообщение
     */
    fun clearChat() {
        viewModelScope.launch {
            aiChatDao.clearChat()

            aiChatDao.insertMessage(
                AiChatEntity(
                    message = "Здравствуйте! Я ваш ИИ-консьерж. Чем могу помочь сегодня?",
                    role = "assistant"
                )
            )
        }
    }

    /**
     * Возвращает текущее время в формате ЧЧ:ММ
     * @return Строка с текущим временем
     */
    private fun currentTime(): String {
        return SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        ).format(Date())
    }
}