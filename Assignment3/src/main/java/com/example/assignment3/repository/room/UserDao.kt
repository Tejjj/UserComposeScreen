package com.example.assignment3.repository.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.assignment3.repository.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun loadUserData(list: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(vararg user: User)

    @Query("SELECT * FROM users")
    fun getObservableUserList() : Flow<List<User>>

    @Delete()
    suspend fun deleteAllUser(userList: List<User>)

    @Query("SELECT * FROM users")
    fun getUserList(): List<User>

    @Query("SELECT * FROM users WHERE userId LIKE :userId")
    suspend fun getUser(userId: Int): User
}