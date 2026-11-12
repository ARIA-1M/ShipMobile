package com.example.mobileship.UseCase

import app.cash.turbine.test
import com.example.mobileship.domain.model.CabinStatus
import com.example.mobileship.domain.repository.CabinRepository
import com.example.mobileship.domain.usecase.FilterCabinUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilterCabinUseCaseTest {

    private lateinit var cabinRepository: CabinRepository
    private lateinit var useCase: FilterCabinUseCase

    @Before
    fun setup() {
        cabinRepository = mockk()
        useCase = FilterCabinUseCase(cabinRepository)
    }

    // Проверка: Если палуба не передана (null), возвращаются все каюты
    @Test
    fun filterCabinsWithNullDeckReturnsAll() = runTest {
        val cabins = listOf(
            CabinStatus("1", 101, 1, "CLEANING"),
            CabinStatus("2", 201, 2, "READY")
        )
        every { cabinRepository.getCabinForCleaning() } returns flowOf(cabins)

        useCase(null).test {
            val result = awaitItem()
            assertEquals(2, result.size)
            awaitComplete()
        }
    }

    // Проверка: Если выбрана 2-я палуба, возвращаются только каюты 2-й палубы
    @Test
    fun filterCabinsWithDeckTwoReturnsOnlyDeckTwo() = runTest {
        val cabins = listOf(
            CabinStatus("1", 101, 1, "CLEANING"),
            CabinStatus("2", 201, 2, "READY")
        )
        every { cabinRepository.getCabinForCleaning() } returns flowOf(cabins)

        useCase(2).test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(201, result[0].cabinNumber)
            awaitComplete()
        }
    }
}