package com.mansao.submissionandroidjetpackpro.data.source.remote

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mansao.submissionandroidjetpackpro.data.source.remote.response.*
import com.mansao.submissionandroidjetpackpro.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    fun getAllMovies(): LiveData<ApiResponse<List<ResultsMovieItem>>> {
        EspressoIdlingResource.increment()
       val resultMovie = MutableLiveData<ApiResponse<List<ResultsMovieItem>>>()

        val client = ApiConfig.getApiService().getMovies()
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    resultMovie.value = response.body()?.let { ApiResponse.success(it.results) }
                    EspressoIdlingResource.decrement()
                }else{
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovie
    }

    fun getAllTvShow(): LiveData<ApiResponse<List<ResultsTvShowItem>>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<ResultsTvShowItem>>>()

        val client = ApiConfig.getApiService().getTvShow()
        client.enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(call: Call<TvShowResponse>,response: Response<TvShowResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        resultTvShow.value = response.body()?.let { ApiResponse.success(it.results) }
                        EspressoIdlingResource.decrement()
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.printStackTrace()}")
                EspressoIdlingResource.decrement()
            }

        })
        return resultTvShow
    }

    fun getDetailMovie(movieId: Int): LiveData<ApiResponse<ResultsMovieItem>>{
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<ResultsMovieItem>>()

        val client = ApiConfig.getApiService().getDetailMovie(movieId)
        client.enqueue(object : Callback<ResultsMovieItem> {
            override fun onResponse(
                call: Call<ResultsMovieItem>,
                response: Response<ResultsMovieItem>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        resultDetailMovie.value = response.body()?.let { ApiResponse.success(it) }
                        EspressoIdlingResource.decrement()
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<ResultsMovieItem>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.printStackTrace()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultDetailMovie
    }

    fun getDetailTvShow(tvShowId: Int): LiveData<ApiResponse<ResultsTvShowItem>> {
        EspressoIdlingResource.increment()
        val resultDetailTvShow = MutableLiveData<ApiResponse<ResultsTvShowItem>>()

        val client = ApiConfig.getApiService().getDetailTvShow(tvShowId)
        client.enqueue(object : Callback<ResultsTvShowItem> {
            override fun onResponse(
                call: Call<ResultsTvShowItem>,
                response: Response<ResultsTvShowItem>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        resultDetailTvShow.value = response.body()?.let { ApiResponse.success(it) }
                        EspressoIdlingResource.decrement()
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<ResultsTvShowItem>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.printStackTrace()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultDetailTvShow
    }


    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }

    }
}