package com.example.assignment3.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlin.random.Random

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val userId: Int,
    val name: String,
    val fullname: String,
    val email: String
)

class ListConverter {
    @TypeConverter
    fun fromList(items: List<String>): String {
        return items.toString()
    }

    @TypeConverter
    fun toList(string: String): List<Char> {
        return string.toList()
    }
}

val nameList = listOf(
    UserStringPair("Mikel", "Jackson"),
    UserStringPair("Mikel", "Jackson"),
    UserStringPair("Steve", "Jobs"),
    UserStringPair("Daniel", "Tiger"),
    UserStringPair("TomCruise", "Cruise")

)
data class UserStringPair(val firstName: String, val lastName: String)

object UserData {
    fun getUserList() : List<User>{
        var userList = mutableListOf<User>()
        nameList.forEachIndexed { index, userStringPair ->
            userList.add(User(index+1,
                name = "${nameList.get(index).firstName}",
                fullname = "${nameList.get(index).lastName}",
                email = "${nameList.get(index).firstName}@gmail.com"))
        }
        return userList
    }

    fun adduser() : User {
        val randomValue = Random.nextInt(1, 4)
        return User(nameList.size+1,
            name = "${nameList.get(randomValue).firstName}",
            fullname = "${nameList.get(randomValue).lastName}",
            email = "${nameList.get(randomValue).firstName}@gmail.com")
    }
}