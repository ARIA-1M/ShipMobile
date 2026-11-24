package com.example.mobileship.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.model.User
import com.example.mobileship.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана авторизации
 */
@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun onQrCodeScanned(
        qrCode: String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                when (val result = loginUseCase(qrCode)) {
                    is DomainResult.Success -> onSuccess(result.data)
                    is DomainResult.Error -> onError(result.message)
                }
            } catch (e: Exception) {
                onError(e.message ?: "Не удалось выполнить авторизацию")
            }
        }
    }
}