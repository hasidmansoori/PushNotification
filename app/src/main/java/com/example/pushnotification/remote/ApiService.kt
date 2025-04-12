package com.example.pushnotification.remote

import com.example.pushnotification.data.entities.Item
import retrofit2.http.GET

interface ApiService {
    @GET("objects")
    suspend fun getObjects(): List<Item>
}
