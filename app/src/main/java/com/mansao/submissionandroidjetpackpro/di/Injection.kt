package com.mansao.submissionandroidjetpackpro.di

import android.content.Context
import com.mansao.submissionandroidjetpackpro.data.WatchRepository
import com.mansao.submissionandroidjetpackpro.data.source.local.LocalDataSource
import com.mansao.submissionandroidjetpackpro.data.source.local.room.WatchDatabase
import com.mansao.submissionandroidjetpackpro.data.source.remote.RemoteDataSource
import com.mansao.submissionandroidjetpackpro.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): WatchRepository {
        val database = WatchDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.watchDao())
        val appExecutors = AppExecutors()

        return WatchRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}