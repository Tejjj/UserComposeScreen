package com.example.usercomposescreen.data

import kotlin.random.Random

data class User(
    val userId: Long,
    val userName: String,
    val fullName: String,
    val email: String
)

val nameList = listOf(
    UserStringPair("Mikel","Jackson"),
    UserStringPair("Mikel","Jackson") ,
    UserStringPair("Steve","Jobs"),
    UserStringPair("Daniel","Tiger"),
    UserStringPair("TomCruise","Cruise"),
    UserStringPair("Rogerrr","Federer"),
    UserStringPair("Rafaelll","Nadal"),
    UserStringPair("Sachin","Tendulkar"),
    UserStringPair("Viratt","Kohli"),
    UserStringPair("Virendar","Sehwag"),
    UserStringPair("Sania", "Mirza")
)

val userDataGenerator = generateSequence {
    val randomValue = Random.nextInt(1, 10)
    User(
        // postId is a 8 digit Long value
        userId = Random.nextLong(10000001, 88888888),
        userName = "${nameList.get(randomValue).firstName}",
        fullName = "${nameList.get(randomValue).lastName}",
        email = "${nameList.get(randomValue).firstName}@gmail.com"
    )
}
data class UserStringPair(val firstName: String, val lastName: String)

object UserData {
    val posts = userDataGenerator.take(100)
}