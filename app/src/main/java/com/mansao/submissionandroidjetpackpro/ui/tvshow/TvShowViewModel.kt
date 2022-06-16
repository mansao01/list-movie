package com.mansao.submissionandroidjetpackpro.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.vo.Resource

class TvShowViewModel(private val watchRepository: WatchRepository) : ViewModel() {

    fun getTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> = watchRepository.getAllTvShow()
}