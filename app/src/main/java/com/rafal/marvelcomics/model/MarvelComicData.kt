package com.rafal.marvelcomics.model

class MarvelComicData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<MarvelComic>
)
