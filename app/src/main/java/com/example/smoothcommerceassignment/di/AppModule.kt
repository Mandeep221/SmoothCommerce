package com.example.smoothcommerceassignment.di

import android.app.Application
import androidx.room.Room
import com.example.smoothcommerceassignment.api.ColoursApi
import com.example.smoothcommerceassignment.data.ColoursDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(ColoursApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesColoursApi(retrofit: Retrofit): ColoursApi {
        return retrofit.create(ColoursApi::class.java)
    }

    @Singleton
    @Provides
    fun providesRoomDb(app: Application): ColoursDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            ColoursDatabase::class.java,
            "colours_db.db"
        ).build()
    }
}