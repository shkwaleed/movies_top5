package com.example.top5movies.ui.movie

import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top5movies.data.local.datastore.UIModeMutableStore
import com.example.top5movies.data.local.datastore.UIModeReadStore
import com.example.top5movies.data.model.ImageResponse
import com.example.top5movies.data.model.Movies
import com.example.top5movies.data.model.MoviesMetadata
import com.example.top5movies.data.model.YearMovies

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
class MovieSharedViewModel @Inject constructor(): ViewModel() {
    private var list = ArrayList<YearMovies> ()
    fun getListData(year: Int):List<MoviesMetadata> {
        var listdata = list.filter { it.year == year }!!
        if(!listdata.isNullOrEmpty()){
            return listdata.get(0).MoviesMetadata!!;
        }else{
            return emptyList()
        }
    }
    fun setListData(year:Int,data:List<MoviesMetadata>) {
        if(!data.isNullOrEmpty()) {
            if(data.get(0).year==year) {
                var listdata = list.filter { it.year == year }
                if (!listdata.isNullOrEmpty()) {
                    list.remove(listdata.get(0))
                }
                list.add(YearMovies(year, data))
            }
        }
    }

}