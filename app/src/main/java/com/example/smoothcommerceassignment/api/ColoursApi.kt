package com.example.smoothcommerceassignment.api

import com.example.smoothcommerceassignment.data.Colour
import retrofit2.http.GET

interface ColoursApi {

    companion object {
        const val BASE_URL = "https://www.colourlovers.com/api/"
    }

    @GET("colors?keywords&format=json&numResults=20")
    suspend fun getColours(): List<Colour>
}