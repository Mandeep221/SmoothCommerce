package com.example.smoothcommerceassignment

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
class ColoursViewModel @Inject constructor(api: ColoursApi) : ViewModel() {

    private val coloursLiveData = MutableLiveData<List<Colour>>()

    init {

        viewModelScope.launch {
            val colours = api.getColours()
            delay(2000)
            coloursLiveData.value = colours
        }
    }

    fun getColours(): LiveData<List<Colour>> = coloursLiveData

}