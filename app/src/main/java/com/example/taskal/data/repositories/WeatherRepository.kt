package com.example.taskal.data.repositories

import android.content.Context
import android.content.SharedPreferences
import com.example.taskal.R
import com.example.taskal.data.network.WeatherApi
import com.example.taskal.data.network.WeatherData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi ,
                                            private val sharedPreferences: SharedPreferences ,
                                            @ApplicationContext private val context : Context
)
{
    suspend fun getWeatherData(latitude : Double , longitude : Double) : WeatherData
    {
        try {
            return weatherApi.getCurrentWeatherData(latitude = latitude , longitude = longitude)
        }
        catch (exception : Exception)
        {
            return WeatherData(null)
        }

    }

    suspend fun logOutUser() : Boolean
    {
        return withContext(Dispatchers.IO) {
            val returnValue = with (sharedPreferences.edit()) {
                putString(context.getString(R.string.userName), "")
                putString(context.getString(R.string.password) , "")
                commit()
            }
            joinAll()
            returnValue
        }
    }
}