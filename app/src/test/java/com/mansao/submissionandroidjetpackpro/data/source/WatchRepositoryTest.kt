package com.mansao.submissionandroidjetpackpro.data.source

import FakeWatchRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mansao.submissionandroidjetpackpro.data.source.local.LocalDataSource
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity
import com.mansao.submissionandroidjetpackpro.data.source.remote.RemoteDataSource
import com.mansao.submissionandroidjetpackpro.utils.AppExecutors
import com.mansao.submissionandroidjetpackpro.utils.DataDummy
import com.mansao.submissionandroidjetpackpro.utils.LiveDataTestUtil
import com.mansao.submissionandroidjetpackpro.utils.PagedListUtil
import com.mansao.submissionandroidjetpackpro.vo.Resource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class WatchRepositoryTest {

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val watchRepository = FakeWatchRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val tvShowResponse = DataDummy.generateRemoteDummyTvShow()
    private val dataMovie = DataDummy.generateDummyMovies()[0]
    private val dataTvShow = DataDummy.generateDummyTvShow()[0]

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovies()).thenReturn(dataSourceFactory)
        watchRepository.getAllMovies()

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getMovies()

        assertNotNull(moviesEntities)
        assertEquals(movieResponses.size.toLong(), moviesEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShow(){
        val dataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getTvShow()).thenReturn(dataSource)
        watchRepository.getAllTvShow()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getTvShow()

        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getDetailMovie(){
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = dataMovie
        `when`(dataMovie.movieId?.let { local.getDetailMovie(it) }).thenReturn(dummyMovie)

        val movieEntities = LiveDataTestUtil.getValue(watchRepository.getDetailMovie(dataMovie.movieId!!))
        verify(local).getDetailMovie(dataMovie.movieId!!)

        assertNotNull(movieEntities)
        assertEquals(dataMovie.movieId, movieEntities.data?.movieId)
    }

    @Test
    fun getDetailTvShow(){
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = dataTvShow
        `when`(dataTvShow.showId?.let { local.getDetailTvShow(it) }).thenReturn(dummyTvShow)

        val tvShowEntities = LiveDataTestUtil.getValue(watchRepository.getDetailTvShow(dataTvShow.showId!!))
        verify(local).getDetailTvShow(dataTvShow.showId!!)

        assertNotNull(tvShowEntities)
        assertEquals(dataTvShow.showId, tvShowEntities.data?.showId)
    }

    @Test
    fun getFavoriteMovies(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovie()).thenReturn(dataSourceFactory)
        watchRepository.getFavoriteMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getFavoriteMovie()

        assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShow(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavoriteTvShow()).thenReturn(dataSourceFactory)
        watchRepository.getFavoriteTvShow()

        val tvShowEntities  = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getFavoriteTvShow()

        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun setFavMovie(){
        val dataDummy = DataDummy.generateDummyMovies()[0].copy(favorite = false)
        Mockito.doNothing().`when`(local).setMovieFavorite(dataDummy, true)
        watchRepository.setMovieFavorite(dataDummy, true)

        verify(local, times(1)).setMovieFavorite(dataDummy, true)
    }

    @Test
    fun setFavTvShow(){
        val dataDummy = DataDummy.generateDummyTvShow()[0].copy(favorite = false)
        doNothing().`when`(local).setTvShowFavorite(dataDummy, true)
        watchRepository.setTvShowFavorite(dataDummy, true)
        verify(local, times(1)).setTvShowFavorite(dataDummy, true)
    }

}