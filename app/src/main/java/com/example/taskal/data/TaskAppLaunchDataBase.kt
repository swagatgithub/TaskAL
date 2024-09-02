package com.example.taskal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskal.data.Entities.User
import com.example.taskal.data.daos.UserDao

@Database(entities = [User::class], version = 1)

abstract class TaskAppLaunchDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
