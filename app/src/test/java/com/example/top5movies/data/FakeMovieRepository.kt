package com.example.top5movies.data

import com.example.top5movies.data.remote.FlickerService
import com.example.top5movies.data.repository.MovieRepository
import com.example.top5movies.utils.mockImageResource
import retrofit2.Response

class FakeMovieRepository(nyService: FlickerService): MovieRepository(nyService) {
    override suspend fun getmovie(name: String) = Response.success(mockImageResource())
}