package com.example.taskal.data.network

import com.example.taskal.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    @GET("onecall")
    suspend fun getCurrentWeatherData(@Query("lat") latitude : Double , @Query("lon") longitude : Double ,
                                      @Query("units") units : String = "imperial" ,
                                      @Query("appid") applicationId : String = BuildConfig.apiKeyOpenWeatherMap ) : WeatherData

}