package com.example.smoothcommerceassignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colours")
data class Colour(
    val title: String,
    @PrimaryKey val hex: String,
    val imageUrl: String,
    var isFavourite: Boolean = false
)