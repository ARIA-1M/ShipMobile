package com.example.mobileship.domain.model

data class Schedule(
    val id: String,
    val title: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val isNow: Boolean = false
)