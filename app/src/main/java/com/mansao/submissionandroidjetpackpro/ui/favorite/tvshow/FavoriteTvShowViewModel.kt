package com.mansao.submissionandroidjetpackpro.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity

class FavoriteTvShowViewModel(private val watchRepository: WatchRepository) : ViewModel() {
    fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> = watchRepository.getFavoriteTvShow()

}