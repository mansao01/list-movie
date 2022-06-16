package com.mansao.submissionandroidjetpackpro.ui.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.vo.Resource

class DetailMovieViewModel(private val watchRepository: WatchRepository) : ViewModel() {

    private var movieId: Int = 0

    fun selectedMovies(movieId: Int) {
        this.movieId = movieId
    }

    fun getMovie(): LiveData<Resource<MovieEntity>> = watchRepository.getDetailMovie(movieId)

    fun setMovieFavorite(movieEntity: MovieEntity, newState: Boolean) =
        watchRepository.setMovieFavorite(movieEntity, newState)
}