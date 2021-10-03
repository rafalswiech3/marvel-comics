package com.rafal.marvelcomics.screens.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rafal.marvelcomics.model.MarvelAPI
import com.rafal.marvelcomics.model.MarvelComic
import com.rafal.marvelcomics.screens.main.MainPagingSource
import com.rafal.marvelcomics.screens.main.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: MarvelAPI
) {
    fun searchComics(title: String): Flow<PagingData<MarvelComic>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 10,
                maxSize = 100
            )
        ) {
            SearchPagingSource(api, title)
        }.flow
    }
}