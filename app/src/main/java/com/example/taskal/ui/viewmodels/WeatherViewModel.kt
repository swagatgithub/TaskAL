package com.example.taskal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskal.data.network.WeatherData
import com.example.taskal.data.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface WeatherUiState {
    data object Empty : WeatherUiState
    data object Loading : WeatherUiState
    data class Success(val weatherData:WeatherData) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

sealed interface LogOutStates {
    data object Empty : LogOutStates
    data object Loading : LogOutStates
    data class Status(val logOutStatus : Boolean) : LogOutStates
}

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    private val _logoutState = MutableStateFlow<LogOutStates>(LogOutStates.Empty)
    val logoutState: StateFlow<LogOutStates> = _logoutState.asStateFlow()

    fun getWeatherData(latitude : Double , longitude : Double)
    {
            viewModelScope.launch {
                _weatherUiState.value = WeatherUiState.Loading
                val returnedWeatherData = weatherRepository.getWeatherData(latitude , longitude)
                _weatherUiState.value = WeatherUiState.Success(returnedWeatherData)
            }
    }

    fun logOutUser()
    {
        viewModelScope.launch {
            _logoutState.value = LogOutStates.Loading
             val returnedLogOutStatus = weatherRepository.logOutUser()
            _logoutState.value = LogOutStates.Status(returnedLogOutStatus)
        }
    }

}