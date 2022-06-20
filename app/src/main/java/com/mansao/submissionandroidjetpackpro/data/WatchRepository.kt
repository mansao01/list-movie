package com.mansao.submissionandroidjetpackpro.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mansao.submissionandroidjetpackpro.data.source.local.LocalDataSource
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity
import com.mansao.submissionandroidjetpackpro.data.source.remote.ApiResponse
import com.mansao.submissionandroidjetpackpro.data.source.remote.RemoteDataSource
import com.mansao.submissionandroidjetpackpro.data.source.remote.response.ResultsMovieItem
import com.mansao.submissionandroidjetpackpro.data.source.remote.response.ResultsTvShowItem
import com.mansao.submissionandroidjetpackpro.utils.AppExecutors
import com.mansao.submissionandroidjetpackpro.vo.Resource

class WatchRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    WatchDataSource {
    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<ResultsMovieItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultsMovieItem>>> =
                remoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<ResultsMovieItem>) {
                val listItem = ArrayList<MovieEntity>()
                for (response in data) {
                    val item = MovieEntity(
                        response.id,
                        response.title,
                        response.overview,
                        response.releaseDate,
                        response.posterPath,
                        false
                    )
                    item.let { listItem.add(it) }
                }
                localDataSource.insertMovie(listItem)
            }
        }.asLiveData()
    }

    override fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<ResultsTvShowItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultsTvShowItem>>> =
                remoteDataSource.getAllTvShow()

            override fun saveCallResult(data: List<ResultsTvShowItem>) {
                val listItem = ArrayList<TvShowEntity>()
                for (response in data) {
                    val item = response.id?.let {
                        TvShowEntity(
                            it,
                            response.title,
                            response.overview,
                            response.firstAirDate,
                            response.posterPath,
                            false
                        )
                    }
                    item?.let {
                        listItem.add(it)
                    }
                    localDataSource.insertTvShow(listItem)
                }
            }
        }.asLiveData()

    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShow(), config).build()
    }

    override fun getDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, ResultsMovieItem>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getDetailMovie(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<ResultsMovieItem>> =
                remoteDataSource.getDetailMovie(movieId)

            override fun saveCallResult(data: ResultsMovieItem) {
                val movieEntity = MovieEntity(
                    data.id,
                    data.title,
                    data.overview,
                    data.releaseDate,
                    data.posterPath,
                    false
                )
                localDataSource.insertDetailMovie(movieEntity)
            }
        }.asLiveData()
    }

    override fun getDetailTvShow(tvShowId: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, ResultsTvShowItem>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> {
                return localDataSource.getDetailTvShow(tvShowId)
            }

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<ResultsTvShowItem>> =
                remoteDataSource.getDetailTvShow(tvShowId)

            override fun saveCallResult(data: ResultsTvShowItem) {
                val tvShowEntity = TvShowEntity(
                    data.id,
                    data.title,
                    data.overview,
                    data.firstAirDate,
                    data.posterPath,
                    false
                )
                localDataSource.insertDetailTvShow(tvShowEntity)
            }

        }.asLiveData()
    }

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, state) }

    override fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setTvShowFavorite(tvShow, state) }

    companion object {
        @Volatile
        private var instance: WatchRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): WatchRepository =
            instance ?: synchronized(this) {
                instance ?: WatchRepository(
                    remoteDataSource,
                    localDataSource,
                    appExecutors
                ).apply {
                    instance = this
                }
            }
    }
}