package com.rafal.marvelcomics.screens.main

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rafal.marvelcomics.model.MarvelAPI
import com.rafal.marvelcomics.model.MarvelComic
import com.rafal.marvelcomics.model.RESULTS_PER_PAGE
import com.rafal.marvelcomics.tools.MarvelAPIHashTool
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val api: MarvelAPI,
    private val title: String
) : PagingSource<Int, MarvelComic>() {
    override fun getRefreshKey(state: PagingState<Int, MarvelComic>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarvelComic> {
        val position = params.key ?: 0

        return try {
            val response = api.searchComics(
                ts = MarvelAPIHashTool.getTimestamp(),
                hash = MarvelAPIHashTool.getHash(),
                offset = position * RESULTS_PER_PAGE,
                limit = RESULTS_PER_PAGE,
                title = title
            )

            val body = response.body()!!
            LoadResult.Page(
                data = body.data.results,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (body.data.results.size < RESULTS_PER_PAGE) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: NullPointerException) {
            LoadResult.Error(exception)
        }
    }
}