package com.example.top5movies.data.repository


import com.example.top5movies.data.remote.FlickerService
import javax.inject.Inject

/*
* The class MovieRepository
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is used to hold movie data operations and conditions
*/
class MovieRepository @Inject constructor(
    private val movieService: FlickerService,
) {
    suspend fun getmovie(name: String) = movieService.getmovie(name = name)
}