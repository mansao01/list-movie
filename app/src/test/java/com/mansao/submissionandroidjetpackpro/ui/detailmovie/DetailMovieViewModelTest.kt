package com.mansao.submissionandroidjetpackpro.ui.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.utils.DataDummy
import com.mansao.submissionandroidjetpackpro.vo.Resource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.movieId

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var watchRepository: WatchRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieEntity>>

    @Before
    fun setup() {
        viewModel = DetailMovieViewModel(watchRepository)
    }

    @Test
    fun getDetailMovie() {
        val dataDummy =Resource.success(DataDummy.generateDummyMovies()[0])
        val movies = MutableLiveData<Resource<MovieEntity>>()
        movies.value = dataDummy

        movieId?.let { viewModel.selectedMovies(it) }

        `when`(watchRepository.getDetailMovie(movieId!!)).thenReturn(movies)
        val result = viewModel.getMovie().value?.data

        movieId.let { verify(watchRepository).getDetailMovie(it) }
        assertNotNull(result)

        assertEquals(dummyMovie.movieId, result?.movieId)
        assertEquals(dummyMovie.movieTitle, result?.movieTitle)
        assertEquals(dummyMovie.moviePoster, result?.moviePoster)
        assertEquals(dummyMovie.movieReleased, result?.movieReleased)
        assertEquals(dummyMovie.movieDescription, result?.movieDescription)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dataDummy)
    }

    @Test
    fun setFavoriteMovie(){
        val dataDummy = Resource.success(DataDummy.generateDummyMovies()[0].copy(favorite = true))
        val dataMovie = MutableLiveData<Resource<MovieEntity>>()
        dataMovie.value = dataDummy

        dataDummy.data?.let { doNothing().`when`(watchRepository).setMovieFavorite(it, false) }
        dataMovie.value?.data?.let { viewModel.setMovieFavorite(it, false) }

        verify(watchRepository).setMovieFavorite(dataMovie.value?.data as MovieEntity, false)
    }
}