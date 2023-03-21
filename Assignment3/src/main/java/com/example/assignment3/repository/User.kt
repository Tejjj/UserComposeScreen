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
    UserStringPair("TomCruise", "Cruise"),
    UserStringPair("Mikel", "Jackson"),
    UserStringPair("Mikel", "Jackson"),
    UserStringPair("Steve", "Jobs"),
    UserStringPair("Daniel", "Tiger"),
    UserStringPair("TomCruise", "Cruise"),
    UserStringPair("Rogerrr", "Federer"),
    UserStringPair("Rafaelll", "Nadal"),
    UserStringPair("Sachin", "Tendulkar"),
    UserStringPair("Viratt", "Kohli"),
    UserStringPair("Virendar", "Sehwag"),
    UserStringPair("Sania", "Mirza")

)
data class UserStringPair(val firstName: String, val lastName: String)

val userDataGenerator = generateSequence {
    val randomValue = Random.nextInt(1, 15)
    User(
        // postId is a 8 digit Long value
        userId = Random.nextInt(1, 1000),
        name = "${nameList.get(randomValue).firstName}",
        fullname = "${nameList.get(randomValue).lastName}",
        email = "${nameList.get(randomValue).firstName}@gmail.com"
    )
}

object UserData {

    val userList = userDataGenerator.take(4).toMutableList()
    fun addUser() : User {
        val randomValue = Random.nextInt(1, 15)

        val user = User(userList.size+1,
            name = "${nameList.get(randomValue).firstName}",
            fullname = "${nameList.get(randomValue).lastName}",
            email = "${nameList.get(randomValue).firstName}@gmail.com")

        userList.add(user)
        return user
    }
}