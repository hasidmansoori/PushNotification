package com.example.pushnotification.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String
)
