package com.rafal.marvelcomics.screens.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafal.marvelcomics.model.MarvelAPI
import com.rafal.marvelcomics.model.MarvelComic
import com.rafal.marvelcomics.tools.MarvelAPIHashTool
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {
    var comicsLoaded = false
    private val _comicsLiveData: MutableLiveData<PagingData<MarvelComic>> =
        MutableLiveData()
    val comicsLiveData: LiveData<PagingData<MarvelComic>> =
        _comicsLiveData

    fun getComics() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllComics().cachedIn(viewModelScope).collect {
                _comicsLiveData.postValue(it)
            }
        }
    }
}