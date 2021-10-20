package com.example.smoothcommerceassignment.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Colour::class],
    version = 1
)
abstract class ColoursDatabase : RoomDatabase() {
    abstract fun getColoursDao(): ColoursDao
}