package com.rafal.marvelcomics.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafal.marvelcomics.model.MarvelComic
import com.rafal.marvelcomics.screens.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepository
) : ViewModel() {
    private val _comicsLiveData: MutableLiveData<PagingData<MarvelComic>> =
        MutableLiveData()
    val comicsLiveData: LiveData<PagingData<MarvelComic>> =
        _comicsLiveData

    fun searchComics(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.searchComics(title).cachedIn(viewModelScope).collect {
                _comicsLiveData.postValue(it)
            }
        }
    }
}