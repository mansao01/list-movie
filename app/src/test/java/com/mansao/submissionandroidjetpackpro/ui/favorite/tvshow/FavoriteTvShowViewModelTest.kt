package com.mansao.submissionandroidjetpackpro.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity
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
class FavoriteTvShowViewModelTest {

    private lateinit var viewModel: FavoriteTvShowViewModel

    @get:Rule
    var instanceTaskExecutors = InstantTaskExecutorRule()

    @Mock
    private lateinit var watchRepository: WatchRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Before
    fun setup() {
        viewModel = FavoriteTvShowViewModel(watchRepository)
    }

    @Test
    fun getFavoriteTvShow() {
        val dummyTvSHow = MutableLiveData<PagedList<TvShowEntity>>()
        dummyTvSHow.value = PagedTestDataSources.snapshot(DataDummy.generateDummyTvShow())
        `when`(watchRepository.getFavoriteTvShow()).thenReturn(dummyTvSHow)

        assertNotNull(dummyTvSHow.value)
        assertEquals(dummyTvSHow.value?.size, viewModel.getFavoriteTvShow().value?.size)
        viewModel.getFavoriteTvShow().observeForever(observer)
        verify(observer).onChanged(dummyTvSHow.value)
    }

    class PagedTestDataSources private constructor(private val items: List<TvShowEntity>) :
        PositionalDataSource<TvShowEntity>() {
        companion object {
            fun snapshot(items: List<TvShowEntity> = listOf()): PagedList<TvShowEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<TvShowEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TvShowEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}