package com.rafal.marvelcomics.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarvelComic(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: MarvelComicThumbnail,
    val creators: MarvelComicCreators,
    val urls: List<MarvelComicUrls>
) : Parcelable {
    @Parcelize
    data class MarvelComicThumbnail(
        val path: String,
        val extension: String
    ) : Parcelable

    @Parcelize
    data class MarvelComicCreators(
        val items: List<MarvelComicCreator>
    ) : Parcelable {

        @Parcelize
        data class MarvelComicCreator(
            val name: String
        ) : Parcelable
    }

    @Parcelize
    data class MarvelComicUrls(
        val type: String,
        val url: String
    ) : Parcelable
}