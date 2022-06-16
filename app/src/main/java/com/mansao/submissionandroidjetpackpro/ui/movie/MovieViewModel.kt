package com.mansao.submissionandroidjetpackpro.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.vo.Resource
class MovieViewModel(private val watchRepository: WatchRepository): ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = watchRepository.getAllMovies()
}