package com.mansao.submissionandroidjetpackpro.ui.detailtvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.vo.Resource

class DetailTvShowViewModel(private val watchRepository: WatchRepository): ViewModel() {
    private var tvShowId: Int =0

    fun selectedTvShow(tvShowId: Int){
        this.tvShowId = tvShowId
    }

    fun getTvShow(): LiveData<Resource<TvShowEntity>> = watchRepository.getDetailTvShow(tvShowId)

    fun setTvShowFavorite(tvShowEntity: TvShowEntity, newState: Boolean ) =
        watchRepository.setTvShowFavorite(tvShowEntity, newState)
}