package com.example.smoothcommerceassignment

import androidx.room.withTransaction
import com.example.smoothcommerceassignment.api.ColoursApi
import com.example.smoothcommerceassignment.data.Colour
import com.example.smoothcommerceassignment.data.ColoursDatabase
import com.example.smoothcommerceassignment.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class ColourRepository @Inject constructor(
    private val api: ColoursApi,
    private val roomDb: ColoursDatabase
) {

    private val coloursDao = roomDb.getColoursDao()

    fun getColours(searchQuery: String) = networkBoundResource(
        query = {
            roomDb.getColoursDao().getAllColours()
        },
        shouldFetch = {
            searchQuery.isNotEmpty()
        },
        fetch = {
            delay(2000)
            api.getColours(searchQuery)
        },
        saveFetchResult = {
            roomDb.withTransaction {
                coloursDao.deleteAllColours()
                coloursDao.insertColour(it)
            }
        }
    )

    suspend fun updateColourAfterLike(colour: Colour) {
        roomDb.getColoursDao().updateFavourite(colour)
    }

    companion object {
        private const val TAG = "ColourRepository"
    }

}