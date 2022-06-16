package com.mansao.submissionandroidjetpackpro.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity
import com.mansao.submissionandroidjetpackpro.data.source.local.room.WatchDao

class LocalDataSource private constructor(private val mWatchDao: WatchDao) {

    fun getMovies(): DataSource.Factory<Int, MovieEntity> = mWatchDao.getMovies()

    fun getTvShow(): DataSource.Factory<Int, TvShowEntity> = mWatchDao.getTvShow()

    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity> = mWatchDao.getFavoriteMovie()

    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity> = mWatchDao.getFavoriteTvShow()

    fun insertMovie(movie: List<MovieEntity>) = mWatchDao.insertMovie(movie)

    fun insertTvShow(tvShow: List<TvShowEntity>) = mWatchDao.insertTvShow(tvShow)

    fun getDetailMovie(movieId: Int): LiveData<MovieEntity> = mWatchDao.getDetailMovie(movieId)

    fun getDetailTvShow(tvSHowId: Int): LiveData<TvShowEntity> = mWatchDao.getDetailTvShow(tvSHowId)

    fun insertDetailMovie(movie: MovieEntity) = mWatchDao.insertDetailMovie(movie)

    fun insertDetailTvShow(tvShow: TvShowEntity) =mWatchDao.insertDetailTvShow(tvShow)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean){
        movie.favorite = newState
        mWatchDao.updateMovie(movie)
    }

    fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean){
        tvShow.favorite = newState
        mWatchDao.updateTvShow(tvShow)
    }


    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(watchDao: WatchDao): LocalDataSource =
            INSTANCE?: LocalDataSource(watchDao)
    }
}