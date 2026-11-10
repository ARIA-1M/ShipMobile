package com.example.mobileship.domain.model

data class CabinStatus(
    val id: String,
    val cabinNumber: Int,
    val deck: Int,
    val status: String
)