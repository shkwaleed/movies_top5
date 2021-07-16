package com.example.top5movies.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.example.top5movies.data.local.datastore.UIModeMutableStore
import com.example.top5movies.data.local.datastore.UIModeReadStore
import com.example.top5movies.data.repository.MovieRepository
import com.example.top5movies.ui.movie.MovieViewModel
import com.example.top5movies.utils.*
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

import org.mockito.Mockito.anyString

import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    //class under test
    private lateinit var viewModel: MovieViewModel
    private var mainCoroutineRule  = MainCoroutineRule()
    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var networkHelper: NetworkHelper
    @Mock
    lateinit var uiModeMutable : UIModeMutableStore
    @Mock
    lateinit var uiModeRead: UIModeReadStore
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()
    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository,networkHelper,uiModeMutable,uiModeRead)
    }

    @Test
    fun `Given mock Image Data When trigger the fetchMoviesImages method , then check  imageResponseLiveData updated`(){
        mainCoroutineRule.runBlockingTest {
            coEvery {movieRepository.getmovie(anyString())  } returns Response.success(mockImageResource())
            viewModel.fetchMovieImage(0, anyString())
            val expectedValue = viewModel.imageRead.getOrAwaitValueTest()
            Truth.assertThat(expectedValue.data).isEqualTo(mockImageResource())
        }
    }


    @After
    fun tearDown() {
    }
}