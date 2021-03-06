package com.rafal.marvelcomics.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val RESULTS_PER_PAGE = 10

interface MarvelAPI {
    @GET("comics")
    suspend fun getComics(
        @Query("ts") ts: Long,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<MarvelComicResponse>

    @GET("comics")
    suspend fun searchComics(
        @Query("ts") ts: Long,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("titleStartsWith") title: String
    ): Response<MarvelComicResponse>
}