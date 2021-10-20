package com.example.smoothcommerceassignment.feature

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.*
import com.example.smoothcommerceassignment.ColourRepository
import com.example.smoothcommerceassignment.ColoursApplication
import com.example.smoothcommerceassignment.data.Colour
import com.example.smoothcommerceassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ColoursViewModel @Inject constructor(
    private val app: Application,
    private val repo: ColourRepository
) : AndroidViewModel(app) {

    companion object {
        private const val TAG = "ColoursViewModel"
    }

    init {
        getColours("")
    }

    private var coloursLiveData = MutableLiveData<Resource<List<Colour>>>()

    fun handleSearchClick(searchKey: String) {
        getColours(searchKey)
    }

    private fun getColours(searchKey: String) {
        viewModelScope.launch {
            repo.getColours(searchKey).collect {
                coloursLiveData.value = it
            }
        }
    }

    fun handleColourLike(colour: Colour) =
        viewModelScope.launch { repo.updateColourAfterLike(colour) }

    fun getUiState(): LiveData<Resource<List<Colour>>> = coloursLiveData

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<ColoursApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (this.type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}