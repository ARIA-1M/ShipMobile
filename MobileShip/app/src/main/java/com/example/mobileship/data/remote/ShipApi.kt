package com.example.mobileship.data.remote

import com.example.mobileship.data.remote.dto.AiRequestDto
import com.example.mobileship.data.remote.dto.AiResponseDto
import com.example.mobileship.data.remote.dto.CabinResponseDto
import com.example.mobileship.data.remote.dto.LoginRequestDto
import com.example.mobileship.data.remote.dto.LoginResponseDto
import com.example.mobileship.data.remote.dto.ScheduleDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ShipApi {

    @GET("api/Schedule")
    suspend fun getSchedule(): List<ScheduleDto>

    @GET("api/Cabin")
    suspend fun getCabins(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50
    ): CabinResponseDto

    @POST("api/Auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @POST("api/Ai/chat")
    suspend fun askAi(@Body request: AiRequestDto): AiResponseDto
}