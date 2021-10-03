package com.rafal.marvelcomics.screens.shared

import com.rafal.marvelcomics.model.MarvelComic

interface IOnRecyclerViewItemClick {
    fun onComicItemClick(comic: MarvelComic)
}