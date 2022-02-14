package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Rick.Rick
import com.example.myapplication.retrofit.RickApi
import com.example.myapplication.retrofit.RickService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ListFragmentViewModel(private val rickApi: RickApi) : ViewModel() {

    private val _ricksFlow = MutableSharedFlow<List<Rick>>()
    val ricksFlow: Flow<List<Rick>> = _ricksFlow.asSharedFlow()

    init {
        loadRick()
    }

    private fun loadRick() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val rickList = RickService.rickApi.getRick(1).results
                print("")
                _ricksFlow.emit(rickList)

            } catch (e: Throwable) {
                _ricksFlow.emit(emptyList())
            }
        }
    }

}
