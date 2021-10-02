package com.rafal.marvelcomics.model

data class MarvelComic(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: MarvelComicThumbnail,
    val creators: MarvelComicCreators
) {
    data class MarvelComicThumbnail(
        val path: String,
        val extension: String
    )

    data class MarvelComicCreators(
        val items: List<MarvelComicCreator>
    ) {
        data class MarvelComicCreator(
            val name: String
        )
    }
}