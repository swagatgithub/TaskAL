package com.example.taskal.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskal.R
import com.example.taskal.ui.viewmodels.LogOutStates
import com.example.taskal.ui.viewmodels.WeatherUiState
import com.example.taskal.ui.viewmodels.WeatherViewModel

@Composable
fun WeatherScreen(weatherViewModel : WeatherViewModel = hiltViewModel() , onLogOutButtonClicked : () -> Unit , onBackButtonClicked : () -> Unit)
{
    val weatherUiState by weatherViewModel.weatherUiState.collectAsState()
    val logOutState by weatherViewModel.logoutState.collectAsState()
    val contextWeatherScreen = LocalContext.current
    val shouldShowCircularProgressIndicator = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        weatherViewModel.getWeatherData(latitude = 12.9082847623315 , longitude = 77.65197822993314)
        weatherViewModel.logoutState.collect{
            when(it)
            {
                is LogOutStates.Loading -> {
                    shouldShowCircularProgressIndicator.value = true
                    println("LogOut.. Loading..")
                }

                is LogOutStates.Status ->{
                    println("LogOut.. Staus")
                    println("Inside LogOut Status..")
                    val logOutStatus = (logOutState as LogOutStates.Status).logOutStatus
                    if(logOutStatus)
                    {
                        onLogOutButtonClicked()
                        Toast.makeText(contextWeatherScreen , R.string.loggedOutSuccessfully , Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(contextWeatherScreen , R.string.requestFailed , Toast.LENGTH_SHORT).show()
                }

                is LogOutStates.Empty -> {
                    println("LogOut.. Empty..")
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        if(shouldShowCircularProgressIndicator.value)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))

        Row(modifier = Modifier.padding(8.dp).fillMaxWidth().wrapContentHeight() , horizontalArrangement = Arrangement.SpaceBetween){
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(8.dp)
                     ,
                text = { Text(text = stringResource(id = R.string.logOut))},
                icon = { Image(painter = painterResource(id = R.drawable.baseline_logout_24), contentDescription = stringResource(
                    id = R.string.logOut
                )) },
                onClick = {
                    weatherViewModel.logOutUser()
                })

            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(8.dp),
                text = { Text(text = stringResource(id = R.string.back))},
                icon = { Image(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = stringResource(
                    id = R.string.back
                )) },
                onClick = { onBackButtonClicked() })
        }

        when (weatherUiState) {

            is WeatherUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is WeatherUiState.Success ->{
                val weatherData = (weatherUiState as WeatherUiState.Success).weatherData
                Column(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .shadow(10.dp)
                    .background(color = Color.LightGray)
                            ) {
                                Text(text ="${stringResource(id = R.string.temprature)} : ${weatherData.current?.temp}" , color = Color.Blue)
                                Text(text = "${stringResource(id = R.string.weatherType)} : ${weatherData.current?.weather?.get(0)?.main}" , color = Color.Blue)
                                Text(text = "${stringResource(id = R.string.humidity)} : ${weatherData.current?.humidity}" , color = Color.Blue)
                                Text(text = "${stringResource(id = R.string.windSpeed)} : ${weatherData.current?.windSpeed}" , color = Color.Blue)

                }
            }
            is WeatherUiState.Error -> {
                Toast.makeText(contextWeatherScreen , R.string.requestFailed , Toast.LENGTH_SHORT).show()
            }

            is WeatherUiState.Empty -> {

            }
        }
    }

}