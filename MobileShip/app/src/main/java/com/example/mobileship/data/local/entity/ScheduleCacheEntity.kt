package com.example.mobileship.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

/**
 * Сущность для кеширования расписания, полученного с сервера.
 * Нужна для офлайн-режима.
 */
@Entity(tableName = "schedule_cache")
data class ScheduleCacheEntity(
    @PrimaryKey val idSchedule: Int,
    val title: String,
    val startTime: String,
    val endTime: String,
    val location: String
)