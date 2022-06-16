package com.mansao.submissionandroidjetpackpro.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(private val watchRepository: WatchRepository): ViewModel() {
    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> = watchRepository.getFavoriteMovie()

}