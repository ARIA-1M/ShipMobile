package com.example.mobileship.Mapper

import com.example.mobileship.data.remote.dto.AiResponseDto
import com.example.mobileship.data.remote.dto.CabinDto
import com.example.mobileship.data.remote.dto.ScheduleDto
import com.example.mobileship.data.local.schedule.toDomain as scheduleToDomain
import com.example.mobileship.data.local.cabin.toDomain as cabinToDomain
import com.example.mobileship.data.local.airesponse.toDomain as aiToDomain

import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    // DTO расписания правильно превращается в Domain-модель Schedule
    @Test
    fun scheduleDtoMapsToDomainCorrectly() {
        val dto = ScheduleDto(
            idSchedule = 1,
            title = "Обед",
            date = "2026-11-15",
            startTime = "12:00:00",
            endTime = "13:00:00",
            location = "Ресторан «Столица»"
        )

        val domain = dto.scheduleToDomain()

        assertEquals("1", domain.id)
        assertEquals("Обед", domain.title)
        assertEquals("12:00:00", domain.startTime)
        assertEquals("13:00:00", domain.endTime)
        assertEquals("Ресторан «Столица»", domain.location)
    }

    // DTO каюты правильно превращается в Domain-модель CabinStatus
    @Test
    fun cabinDtoMapsToDomainAndCalculatesDeck() {
        val dto = CabinDto(
            idCabin = 101,
            number = 301,
            deck = 3,
            statusName = "CLEANING"
        )

        val domain = dto.cabinToDomain()

        assertEquals("101", domain.id)
        assertEquals(301, domain.cabinNumber)
        assertEquals(3, domain.deck)
        assertEquals("CLEANING", domain.status)
    }

    // DTO ответа ИИ правильно превращается в Domain-модель AiResponse
    @Test
    fun aiResponseDtoMapsToDomainCorrectly() {
        val dto = AiResponseDto(
            role = "assistant",
            content = "Привет! Я ваш ИИ-консьерж."
        )

        val domain = dto.aiToDomain()

        assertEquals("assistant", domain.role)
        assertEquals("Привет! Я ваш ИИ-консьерж.", domain.content)
    }
}