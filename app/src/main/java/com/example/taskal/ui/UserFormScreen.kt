package com.example.taskal.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskal.R
import com.example.taskal.data.Entities.User
import com.example.taskal.ui.viewmodels.UserFormStates
import com.example.taskal.ui.viewmodels.UserViewModel

@Composable
fun UserFormScreen( userViewModel : UserViewModel = hiltViewModel() , saveSuccessAndCancelCallback : () -> Unit)
{
    var firstNameTextFieldValue by remember { mutableStateOf("") }

    var lastNameTextFieldValue by remember { mutableStateOf("") }

    var emailTextFieldValue by remember { mutableStateOf("") }

    val shouldShowCircularProgressIndicator = remember { mutableStateOf(false) }

    val contextUserFormScreen = LocalContext.current

    LaunchedEffect(Unit) {
        userViewModel.userFormState.collect{
            when (it) {
                is UserFormStates.Loading ->
                    shouldShowCircularProgressIndicator.value = true
                is UserFormStates.Success -> {
                    Toast.makeText(contextUserFormScreen , it.successMessage , Toast.LENGTH_SHORT).show()
                    saveSuccessAndCancelCallback()
                }
                is UserFormStates.Error ->
                    Toast.makeText(contextUserFormScreen , it.errorMessage , Toast.LENGTH_SHORT).show()
                is UserFormStates.Empty -> {

                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {

        if (shouldShowCircularProgressIndicator.value)
            CircularProgressIndicator()

        TextField(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(), value = firstNameTextFieldValue , onValueChange = {
            firstNameTextFieldValue = it
        } , label = {
            Text(text = stringResource(id = R.string.firstName))
        } , singleLine = true)

        TextField(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(), value = lastNameTextFieldValue , onValueChange = {
            lastNameTextFieldValue = it
        } , label = {
            Text(text = stringResource(id = R.string.lastName))
        } , singleLine = true)

        TextField(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(), value = emailTextFieldValue , onValueChange = {
            emailTextFieldValue = it
        } , label = {
            Text(text = stringResource(id = R.string.email))
        } , singleLine = true)

        Row {

            Button(onClick = { saveSuccessAndCancelCallback() }) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Spacer(modifier = Modifier.size(8.dp))

            Button(onClick = {
                if(firstNameTextFieldValue.isEmpty() && lastNameTextFieldValue.isEmpty() && emailTextFieldValue.isEmpty())
                    Toast.makeText(contextUserFormScreen , R.string.pleaseEnterDetails , Toast.LENGTH_SHORT).show()
                else if(firstNameTextFieldValue.isEmpty())
                    Toast.makeText(contextUserFormScreen , R.string.pleaseEnterFirstName , Toast.LENGTH_SHORT).show()
                else if(lastNameTextFieldValue.isEmpty())
                    Toast.makeText(contextUserFormScreen , R.string.pleaseEnterLastName , Toast.LENGTH_SHORT).show()
                else if(emailTextFieldValue.isEmpty())
                    Toast.makeText(contextUserFormScreen , R.string.pleaseEnterEmail , Toast.LENGTH_SHORT).show()
                else
                    userViewModel.addNewUser(User(firstName = firstNameTextFieldValue , lastName = lastNameTextFieldValue , email = emailTextFieldValue))
            }) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun UserFormScreenPreview()
{
    UserFormScreen{

    }
}

