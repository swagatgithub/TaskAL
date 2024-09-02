package com.example.taskal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskal.R
import com.example.taskal.ui.viewmodels.UserViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskal.ui.viewmodels.UserStates

@Composable
fun UsersListScreen(userViewModel : UserViewModel = hiltViewModel() , onAddButtonClicked : () -> Unit , onUserDetailsColumnClicked : () -> Unit) {

    val userListState by userViewModel.userListState.collectAsState()

    LaunchedEffect(Unit){
        userViewModel.getUserList()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        
        when (userListState) {
            is UserStates.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is UserStates.Success ->{
                val userList = (userListState as UserStates.Success).listOfUser
                if(userList.isEmpty())
                    Text(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.Center),
                        text = stringResource(id = R.string.noUserToShow) , textAlign = TextAlign.Center ,
                        fontWeight = FontWeight.Medium , fontSize = 16.sp)
                else
                {
                    LazyColumn(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()){
                        items(items = userList){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(bottom = 8.dp)
                                    .shadow(10.dp)
                                    .background(color = Color.LightGray).clickable { onUserDetailsColumnClicked() }
                            ) {
                                Text(text ="${stringResource(id = R.string.firstName)} : ${it.firstName}" , color = Color.Blue)
                                Text(text = "${stringResource(id = R.string.lastName)} : ${it.lastName}" , color = Color.Blue)
                                Text(text = "${stringResource(id = R.string.email)} : ${it.email}" , color = Color.Blue)
                            }
                        }
                    }
                }
            }

            is UserStates.Error -> {

            }
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopEnd),
            text = { Text(text = stringResource(id = R.string.add))},
            icon = { Image(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = stringResource(
                id = R.string.add
            )) },
            onClick = { onAddButtonClicked() })
    }


}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun UsersListScreenPreview()
{
    UsersListScreen(onAddButtonClicked = {}){

    }
}