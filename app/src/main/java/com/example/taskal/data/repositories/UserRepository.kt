package com.example.taskal.data.repositories

import com.example.taskal.data.Entities.User
import com.example.taskal.data.TaskAppLaunchDataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val taskAppLaunchDataBase: TaskAppLaunchDataBase)
{
    fun getActualUserList() : Flow<List<User>>
    {
        return taskAppLaunchDataBase.userDao().getAll()
    }

    suspend fun addNewUserToLocalDB(newUser : User) : Long
    {
        return taskAppLaunchDataBase.userDao().addUser(newUser)
    }
}