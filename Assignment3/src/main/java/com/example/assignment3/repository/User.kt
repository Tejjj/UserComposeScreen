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
