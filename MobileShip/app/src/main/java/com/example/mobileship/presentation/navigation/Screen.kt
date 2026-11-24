package com.example.mobileship.presentation.navigation

/**
 * Объект содержащий константы маршрутов навигации
 */
object Screen {
    const val Splash = "splash"
    const val Authorization = "authorization"
    const val Home = "home/{cabinNumber}"
    fun home(cabinNumber: Int): String = "home/$cabinNumber"
    const val Schedule = "schedule"
    const val Chat = "chat"
    const val Cities = "cities"
    const val InformationCities = "information_cities"
    const val Alarms = "alarms"
    const val StatusCabin = "status_cabin"
}
