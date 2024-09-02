package com.example.taskal.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.taskal.R
import com.example.taskal.data.TaskAppLaunchDataBase
import com.example.taskal.data.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskAppLaunchModule {

    @Singleton
    @Provides
    fun getSharedPreference(@ApplicationContext context : Context) : SharedPreferences
    {
        return context.getSharedPreferences(context.getString(R.string.loginCredentials) , Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTaskAppLaunchDataBase(
        @ApplicationContext context: Context
    ): TaskAppLaunchDataBase {

        return Room.databaseBuilder(
            context,
            TaskAppLaunchDataBase::class.java,
            "taskAppLaunch_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(WeatherApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit):WeatherApi
    {
        return retrofit.create(WeatherApi::class.java)
    }

}