package com.example.taskal.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskal.R
import com.example.taskal.ui.theme.TaskALTheme

@Composable
fun OnBoardingScreen(onGoToLoginButtonClicked : () -> Unit)
{
    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally){

        Text(text = stringResource(id = R.string.welcome) , fontSize = 24.sp , fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = { onGoToLoginButtonClicked() }) {
            Text(text = stringResource(id = R.string.goToLoginScreen))
        }
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun OnBoardingScreenPreview() {

    TaskALTheme {
        OnBoardingScreen{
            Log.d("Empty" , "Function Literal Body..")
        }
    }

}