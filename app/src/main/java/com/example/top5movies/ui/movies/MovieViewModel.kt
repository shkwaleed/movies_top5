package com.example.top5movies.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top5movies.data.local.datastore.UIModeMutableStore
import com.example.top5movies.data.local.datastore.UIModeReadStore
import com.example.top5movies.data.model.ImageResponse

import com.example.top5movies.data.repository.MovieRepository
import com.example.top5movies.utils.NO_INTERNET_CONNECTION
import com.example.top5movies.utils.NetworkHelper
import com.example.top5movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* The class MovieViewModel
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is used to initiate the API call for the movie, send data in case the api hits and get the respective data, send error message in case the api isn't working
* well, or the internet connection isn't available. It also used to update the night mode and day mode.
*/
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val networkHelper: NetworkHelper,
    private val uiModeEdit: UIModeMutableStore,
    val uiModeRead: UIModeReadStore,
) : ViewModel() {
    private val ImageResponse = MutableLiveData<Resource<ImageResponse>>()
    val imageRead: LiveData<Resource<ImageResponse>>
        get() = ImageResponse

    init {
      //  fetchmovie()
        //test commit
    }

     fun fetchMovieImage(index:Int,name:String) {
        viewModelScope.launch {
            ImageResponse.postValue(Resource.loading(null))


            if (networkHelper.isNetworkConnected()) {
                movieRepository.getmovie(name).let {
                    Log.e("test_data", it.toString())
                    if (it.isSuccessful) {
                        it.body()!!.index=index
                        ImageResponse.postValue(Resource.success(it.body()))
                        print("success ${it.body()}")
                    } else {
                        print("error ${it.body()}")
                        ImageResponse.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                print("network error")
                ImageResponse.postValue(Resource.error(NO_INTERNET_CONNECTION, null))
            }
        }
    }

    // Save to DataStore
    fun updateUiModeState(isNightMode: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        uiModeEdit.saveToDataStore(isNightMode)
    }



}