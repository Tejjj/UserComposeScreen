package com.example.assignment3.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.assignment3.repository.ListConverter
import com.example.assignment3.repository.User
import javax.inject.Inject

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(*[ListConverter::class])
abstract class UserDatabase() : RoomDatabase() {

    abstract val userDao: UserDao

}