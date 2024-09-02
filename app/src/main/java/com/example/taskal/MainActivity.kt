package com.example.taskal

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.taskal.ui.theme.TaskALTheme
import com.example.taskal.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var sharedPreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var startDestination = Routes.UsersList.name
            if(sharedPreference.getString(getString(R.string.userName) , "")?.isEmpty()!!) {
                startDestination = Routes.OnBoarding.name
                println("Inside If..")
            }
            TaskALTheme {
                    TaskAppLaunch(startDestination = startDestination)
                }
            }
        }
}
