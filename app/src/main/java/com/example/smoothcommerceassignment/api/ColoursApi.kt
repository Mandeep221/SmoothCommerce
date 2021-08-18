package com.example.smoothcommerceassignment.api

import com.example.smoothcommerceassignment.data.Colour
import retrofit2.http.GET
import retrofit2.http.Query

interface ColoursApi {

    companion object {
        const val BASE_URL = "https://www.colourlovers.com/api/"
    }

    @GET("colors?format=json&numResults=20")
    suspend fun getColours(@Query("keywords") searchKey: String): List<Colour>
}