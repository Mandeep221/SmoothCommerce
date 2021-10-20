package com.example.smoothcommerceassignment.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ColoursDao {

    @Query("SELECT * FROM colours")
    fun getAllColours(): Flow<List<Colour>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColour(colours: List<Colour>)

    @Query("DELETE FROM colours")
    suspend fun deleteAllColours()

    @Update
    suspend fun updateFavourite(colour: Colour)
}