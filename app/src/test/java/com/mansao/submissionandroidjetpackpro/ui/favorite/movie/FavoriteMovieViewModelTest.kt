package com.mansao.submissionandroidjetpackpro.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest{

    private lateinit var viewModel: FavoriteMovieViewModel

    @get:Rule
    var instanceTaskExecutors = InstantTaskExecutorRule()

    @Mock
    private lateinit var watchRepository: WatchRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Before
    fun setup(){
        viewModel = FavoriteMovieViewModel(watchRepository)
    }

    @Test
    fun getFavoriteMovie(){
        val dummyMovie = MutableLiveData<PagedList<MovieEntity>>()
        dummyMovie.value = PagedTestDataSources.snapshot(DataDummy.generateDummyMovies())
        `when`(watchRepository.getFavoriteMovie()).thenReturn(dummyMovie)

        assertNotNull(dummyMovie.value)
        assertEquals(dummyMovie.value?.size, viewModel.getFavoriteMovie().value?.size )
        viewModel.getFavoriteMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovie.value)
    }
    class PagedTestDataSources private constructor(private val items: List<MovieEntity>) :
        PositionalDataSource<MovieEntity>() {
        companion object {
            fun snapshot(items: List<MovieEntity> = listOf()): PagedList<MovieEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<MovieEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}