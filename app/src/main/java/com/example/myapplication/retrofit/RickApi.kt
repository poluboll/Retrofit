package com.example.myapplication.retrofit

import com.example.myapplication.Rick.RickInfo
import com.example.myapplication.Rick.RickList
import com.example.myapplication.Rick.RickListInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickApi {
    @GET("character")
    suspend fun getRick(
        @Query("pages") pages: Int
    ): RickList

    @GET("character")
   suspend fun getRickInfo(
        @Query("name") name: String
    ): RickListInfo
}