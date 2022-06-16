package com.mansao.submissionandroidjetpackpro.data.source.remote

import com.mansao.submissionandroidjetpackpro.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMovies(
        @Query("api_key") apiKey: String = "753e9830cd4e603ae28d706973ed36dd"
    ) : Call<MovieResponse>

//    @GET("discover/movie")
//    fun getMovies(
//        @Query("api_key") apiKey: String = "753e9830cd4e603ae28d706973ed36dd"
//    ) : Call<MovieResponse>

    @GET("tv/popular")
    fun getTvShow(
        @Query("api_key") apiKey: String = "753e9830cd4e603ae28d706973ed36dd"
    ) : Call<TvShowResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "753e9830cd4e603ae28d706973ed36dd"
    ): Call<ResultsMovieItem>

    @GET("tv/{tv_show_id}")
    fun getDetailTvShow(
        @Path("tv_show_id") tvShowId: Int,
        @Query("api_key") apiKey: String = "b60c850ae4a336041a238999da15e0f0"
    ): Call<ResultsTvShowItem>
}