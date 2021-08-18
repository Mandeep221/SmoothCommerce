package com.example.smoothcommerceassignment.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smoothcommerceassignment.api.ColoursApi
import com.example.smoothcommerceassignment.data.Colour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ColoursViewModel @Inject constructor(private val api: ColoursApi) : ViewModel() {

    private val coloursLiveData = MutableLiveData<List<Colour>>()

    fun handleSearchClick(searchKey: String) {
        viewModelScope.launch {
            val colours = api.getColours(searchKey)
            delay(2000)
            coloursLiveData.value = colours
        }
    }

    fun getColours(): LiveData<List<Colour>> = coloursLiveData
}