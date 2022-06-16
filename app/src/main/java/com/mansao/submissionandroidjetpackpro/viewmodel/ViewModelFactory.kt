package com.mansao.submissionandroidjetpackpro.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.ui.detailmovie.DetailMovieViewModel
import com.mansao.submissionandroidjetpackpro.ui.detailtvshow.DetailTvShowViewModel
import com.mansao.submissionandroidjetpackpro.di.Injection
import com.mansao.submissionandroidjetpackpro.ui.favorite.movie.FavoriteMovieViewModel
import com.mansao.submissionandroidjetpackpro.ui.favorite.tvshow.FavoriteTvShowViewModel
import com.mansao.submissionandroidjetpackpro.ui.movie.MovieViewModel
import com.mansao.submissionandroidjetpackpro.ui.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val mWatchRepository: WatchRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) ->{
                MovieViewModel(mWatchRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) ->{
                TvShowViewModel(mWatchRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) ->{
                DetailMovieViewModel(mWatchRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvShowViewModel::class.java) ->{
                DetailTvShowViewModel(mWatchRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) ->{
                FavoriteMovieViewModel(mWatchRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) ->{
                FavoriteTvShowViewModel(mWatchRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel Class : ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }
}