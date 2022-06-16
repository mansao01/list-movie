package com.mansao.submissionandroidjetpackpro.ui.detailtvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.utils.DataDummy
import com.mansao.submissionandroidjetpackpro.vo.Resource
import com.nhaarman.mockitokotlin2.doNothing
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvShowViewModelTest {
    private lateinit var viewModel: DetailTvShowViewModel
    private val dummyTvShow = DataDummy.generateDummyTvShow()[0]
    private val tvShowId = dummyTvShow.showId

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var watchRepository: WatchRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvShowEntity>>

    @Before
    fun setup(){
        viewModel = DetailTvShowViewModel(watchRepository)
    }

    @Test
    fun getDetailTvShow() {
        val dataDummy = Resource.success(DataDummy.generateDummyTvShow()[0])
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dataDummy

        tvShowId?.let { viewModel.selectedTvShow(it) }

        `when`(watchRepository.getDetailTvShow(tvShowId!!)).thenReturn(tvShow)
        val result = viewModel.getTvShow().value?.data

        tvShowId.let { verify(watchRepository).getDetailTvShow(it) }
        assertNotNull(result)

        assertEquals(dummyTvShow.showId, result?.showId)
        assertEquals(dummyTvShow.showTitle, result?.showTitle)
        assertEquals(dummyTvShow.showPoster, result?.showPoster)
        assertEquals(dummyTvShow.showReleased, result?.showReleased)
        assertEquals(dummyTvShow.showDescription, result?.showDescription)

        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(dataDummy)
    }

    @Test
    fun setFavoriteTvShow(){
        val dataDummy  = Resource.success(DataDummy.generateDummyTvShow()[0].copy(favorite = false))
        val dataTvShow = MutableLiveData<Resource<TvShowEntity>>()
        dataTvShow.value = dataDummy

        dataDummy.data?.let { doNothing().`when`(watchRepository).setTvShowFavorite(it, false) }
        dataTvShow.value?.data?.let { viewModel.setTvShowFavorite(it, false) }

        verify(watchRepository).setTvShowFavorite(dataTvShow.value?.data as TvShowEntity, false )



    }
}