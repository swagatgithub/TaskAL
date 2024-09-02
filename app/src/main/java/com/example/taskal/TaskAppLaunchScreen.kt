package com.example.taskal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskal.ui.LoginScreen
import com.example.taskal.ui.OnBoardingScreen
import com.example.taskal.ui.UserFormScreen
import com.example.taskal.ui.UsersListScreen
import com.example.taskal.ui.WeatherScreen
import com.example.taskal.utils.Routes

@Composable
fun TaskAppLaunch(navController: NavHostController = rememberNavController() , startDestination : String)
{
    Scaffold { innerPadding ->
        NavHost(navController = navController, startDestination = startDestination ,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {

            composable(route = Routes.OnBoarding.name) {
                OnBoardingScreen {
                    navController.navigate(Routes.Login.name)
                }
            }

            composable(route = Routes.Login.name) { LoginScreen{
                navController.navigate(Routes.UsersList.name){
                    popUpTo(Routes.OnBoarding.name){
                        inclusive = true
                    }
                }
             }
            }

            composable(route = Routes.UsersList.name){
                UsersListScreen(onAddButtonClicked = {
                    navController.navigate(Routes.UserForm.name)
                }){
                    navController.navigate(Routes.Weather.name)
                }
            }

            composable(route = Routes.UserForm.name){
                UserFormScreen{
                    navController.popBackStack()
                }
            }

            composable(route = Routes.Weather.name){
                WeatherScreen(onLogOutButtonClicked = { navController.navigate(Routes.Login.name){
                    popUpTo(Routes.UsersList.name){
                        inclusive = true
                    }
                } }) {
                    navController.popBackStack()
                }
            }
        }
    }
}