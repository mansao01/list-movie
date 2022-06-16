package com.mansao.submissionandroidjetpackpro.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.mansao.submissionandroidjetpackpro.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var watchRepository: WatchRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Before
    fun setup(){
        viewModel = MovieViewModel(watchRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovie = PagedTestDataSources.snapshot(DataDummy.generateDummyMovies())
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = Resource.success(dummyMovie)

        `when`(watchRepository.getAllMovies()).thenReturn(movies)

        assertNotNull(viewModel.getMovies().value)
        assertEquals(movies.value, viewModel.getMovies().value)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(movies.value)
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