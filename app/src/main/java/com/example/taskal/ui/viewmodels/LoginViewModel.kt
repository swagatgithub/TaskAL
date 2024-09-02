package com.example.taskal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskal.data.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LoginStates {
    data object Empty : LoginStates
    data object Loading : LoginStates
    data object Success : LoginStates
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository : LoginRepository) : ViewModel()
{
    private val _loginState = MutableStateFlow<LoginStates>(LoginStates.Empty)
    val loginState: StateFlow<LoginStates> = _loginState.asStateFlow()

    fun startLogin() {
        viewModelScope.launch {
            _loginState.value = LoginStates.Loading
            val returnValue = loginRepository.loginIntoApplication()
            if (returnValue)
                _loginState.value = LoginStates.Success
        }
    }
}