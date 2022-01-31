package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Rick.RickListInfo
import com.example.myapplication.retrofit.RickApi
import com.example.myapplication.retrofit.RickService.rickApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class InfoFragmentViewModel(
    private val rickApi: RickApi,
    private val name: String
) : ViewModel() {

    val rickDetailsFlow: Flow<RickListInfo> = flow {
        emit(rickApi.getRickInfo(name))
    }.shareIn(viewModelScope,started= SharingStarted.Eagerly,replay = 1)
}