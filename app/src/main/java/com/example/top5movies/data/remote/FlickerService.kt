package com.example.top5movies.data.remote

import com.example.top5movies.data.model.ImageResponse
import com.example.top5movies.utils.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
/*
* The interface NYService
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This interface us used to declare the movie api access points
*/

interface FlickerService {
    @GET(FLICKER_END_POINT)
    suspend fun getmovie(@Query("method") method: String = METHOD,
                         @Query("api_key") api_key: String = API_KEY,
                         @Query("format") format: String = FORMAT,
                         @Query("nojsoncallback") nojsoncallback: String = NOJSONCALLBACK,
                         @Query("text") name: String ,
                         @Query("page") page: String = PAGE,
                         @Query("per_page") per_page: String = PERPAGE
                         ): Response<ImageResponse>
}