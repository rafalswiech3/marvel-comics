package com.rafal.marvelcomics.model

data class MarvelComicResponse(
    val code: Int,
    val status: String,
    val data: MarvelComicData
)