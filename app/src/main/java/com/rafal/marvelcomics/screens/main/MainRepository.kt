package com.rafal.marvelcomics.screens.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rafal.marvelcomics.model.MarvelAPI
import com.rafal.marvelcomics.model.MarvelComic
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: MarvelAPI
) {
    fun getAllComics(): Flow<PagingData<MarvelComic>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 10,
                maxSize = 100
            )
        ) {
            MainPagingSource(api)
        }.flow
    }
}