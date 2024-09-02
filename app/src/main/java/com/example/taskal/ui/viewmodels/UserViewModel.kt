package com.example.taskal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskal.data.Entities.User
import com.example.taskal.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UserStates {
    data object Loading : UserStates
    data class Success(val listOfUser: List<User>) : UserStates
    data class Error(val message: String) : UserStates
}

sealed interface UserFormStates {
    data object Loading : UserFormStates
    data object Empty : UserFormStates
    data class Success(val successMessage : String) : UserFormStates
    data class Error(val errorMessage : String) : UserFormStates
}

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel()
{

    private val _userListState = MutableStateFlow<UserStates>(UserStates.Loading)
    val userListState: StateFlow<UserStates> = _userListState.asStateFlow()

    private val _userFormState = MutableStateFlow<UserFormStates>(UserFormStates.Empty)
    val userFormState: StateFlow<UserFormStates> = _userFormState.asStateFlow()

    fun getUserList()
    {
        viewModelScope.launch {
            val returnedListOfUser = userRepository.getActualUserList()
            returnedListOfUser.collect{
                _userListState.value = UserStates.Success(it)
            }
        }
    }

    fun addNewUser(newUser : User)
    {
        val insertionFail : Long = -1
        _userFormState.value = UserFormStates.Loading
        viewModelScope.launch {
            val insertionStatus = userRepository.addNewUserToLocalDB(newUser)
            if(insertionStatus != insertionFail)
                _userFormState.value = UserFormStates.Success("User Saved Successfully.")
            else
                _userFormState.value = UserFormStates.Error("Save Failed , please try again later.")
        }
    }
}