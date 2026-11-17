package com.example.mobileship.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

/**
 * Класс для управления настройками пользователя через DataStore
 */
class UserPreferences(private val context: Context) {

    private object Keys {
        val USER_ROLE = stringPreferencesKey("user_role")
        val CABIN_NUMBER = stringPreferencesKey("cabin_number")
    }

    /**
     * Получает роль текущего пользователя
     * @return Flow со строкой роли
     */
    val userRole: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[Keys.USER_ROLE] }

    /**
     * Сохраняет роль пользователя
     * @param role Новая роль
     */
    suspend fun saveUserRole(role: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.USER_ROLE] = role
        }
    }
}