package com.example.taskal.data.network

import com.google.gson.annotations.SerializedName

data class WeatherData(
	val current: Current?,
)

data class WeatherItem(
	val icon: String,
	val description: String,
	val main: String,
	val id: Int
)

data class Current(
	val temp: Double,
	val weather: List<WeatherItem>,
	val humidity: Int,
	@SerializedName("wind_speed")val windSpeed: Double)

