package com.example.mobileship.data.remote

import com.example.mobileship.data.remote.dto.AiRequestDto
import com.example.mobileship.data.remote.dto.AiResponseDto
import com.example.mobileship.data.remote.dto.CabinDto
import com.example.mobileship.data.remote.dto.CabinResponseDto
import com.example.mobileship.data.remote.dto.ScheduleDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Интерфейс для описания сетевых запросов к API сервера
 */
interface ShipApi {

    /**
     * Получает расписание мероприятий на день
     * @return Список объектов ScheduleDto
     */
    @GET("api/Schedule")
    suspend fun getSchedule(): List<ScheduleDto>

    /**
     * Получает список кают с пагинацией
     * @param page Номер страницы
     * @param limit Количество записей на странице
     * @return Объект CabinResponseDto со списком кают
     */
    @GET("api/Cabin")
    suspend fun getCabins(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100
    ): CabinResponseDto

    /**
     * Получает список кают с активными сигналами тревоги
     * @return Список кают с тревогами
     */
    @GET("api/Cabin/alarms")
    suspend fun getAlarms(): List<CabinDto>

    /**
     * Отправляет запрос к ИИ-ассистенту
     * @param request Объект с сообщениями и контекстом
     * @return Ответ от нейросети
     */
    @POST("api/Ai/chat")
    suspend fun askAi(@Body request: AiRequestDto): AiResponseDto
}