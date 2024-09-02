package com.example.taskal.data.repositories

import android.content.Context
import android.content.SharedPreferences
import com.example.taskal.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(private val sharedPreferences: SharedPreferences , @ApplicationContext private val context : Context)
{
    suspend fun loginIntoApplication() : Boolean
    {
        return withContext(Dispatchers.IO) {
            val returnValue = with (sharedPreferences.edit()) {
                putString(context.getString(R.string.userName), "testapp@google.com")
                putString(context.getString(R.string.password) , "Test@123456")
                commit()
            }
            joinAll()
            returnValue
        }
    }
}