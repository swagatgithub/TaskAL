package com.example.taskal.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskal.R
import com.example.taskal.ui.viewmodels.LoginStates
import com.example.taskal.ui.viewmodels.LoginViewModel


@Composable
fun LoginScreen(loginViewModel : LoginViewModel = hiltViewModel() , onLoginSuccessful : () -> Unit)
{
    var userNameTextFieldValue by remember { mutableStateOf("") }

    var passwordTextFieldValue by remember { mutableStateOf("") }

    val shouldShowCircularProgressIndicator = remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {

        loginViewModel.loginState.collect {

            when (it) {

                is LoginStates.Loading -> {
                    shouldShowCircularProgressIndicator.value = true
                }

                is LoginStates.Success -> {
                    println("LoginStates Success..")
                    Toast.makeText(context , R.string.loginSuccessFul , Toast.LENGTH_SHORT).show()
                    onLoginSuccessful()
                }

                is LoginStates.Empty ->
                {

                }

            }
        }
    }

    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {

        if (shouldShowCircularProgressIndicator.value)
            CircularProgressIndicator()

        TextField(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(), value = userNameTextFieldValue , onValueChange = {
            userNameTextFieldValue = it
        } , label = {
            Text(text = stringResource(id = R.string.userName))
        } , singleLine = true)

        Spacer(modifier = Modifier.size(16.dp))

        TextField(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth() , value = passwordTextFieldValue , onValueChange = {
            passwordTextFieldValue = it
        } , label = {
            Text(text = stringResource(id = R.string.password))
        } , singleLine = true ,
            visualTransformation = PasswordVisualTransformation())

        Button(onClick = {
            if(userNameTextFieldValue.isEmpty() && passwordTextFieldValue.isEmpty())
                Toast.makeText(context , R.string.pleaseEnterUserNameAndPassword , Toast.LENGTH_SHORT).show()
            else if(userNameTextFieldValue.isEmpty())
                Toast.makeText(context , R.string.pleaseEnterUserName , Toast.LENGTH_SHORT).show()
            else if(passwordTextFieldValue.isEmpty())
                Toast.makeText(context , R.string.pleaseEnterPassWord , Toast.LENGTH_SHORT).show()
            else{
                if(userNameTextFieldValue == "testapp@google.com" && passwordTextFieldValue == "Test@123456")
                    loginViewModel.startLogin()
                else
                    Toast.makeText(context , R.string.pleaseEnterCorrectCredentials , Toast.LENGTH_SHORT).show()
            }
        } , modifier = Modifier.padding(vertical = 28.dp)) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun LoginScreenPreview()
{
    LoginScreen{

    }
}
