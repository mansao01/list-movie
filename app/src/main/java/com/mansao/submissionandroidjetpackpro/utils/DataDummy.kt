package com.mansao.submissionandroidjetpackpro.utils

import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity
import com.mansao.submissionandroidjetpackpro.data.source.remote.response.ResultsMovieItem
import com.mansao.submissionandroidjetpackpro.data.source.remote.response.ResultsTvShowItem

object DataDummy {
    fun generateDummyMovies(): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(
            MovieEntity(
                566525,
                "No Time to Die",
                "Bond has left active service and is enjoying a tranquil life in Jamaica. His peace is short-lived when his old friend Felix Leiter from the CIA turns up asking for help. The mission to rescue a kidnapped scientist turns out to be far more treacherous than expected, leading Bond onto the trail of a mysterious villain armed with dangerous new technology.",
                "2021-09-29",
                "https://image.tmdb.org/t/p/w500/xeItgLK9qcafxbd8kYgv7XnMEog.jpg"
            )
        )
        return movies
    }

    fun generateDummyTvShow(): List<TvShowEntity> {
        val tvShow = ArrayList<TvShowEntity>()
        tvShow.add(
            TvShowEntity(
                90462,
                "Days of Our Lives",
                "The Horton and Brady broods endure the romantic trials of life in Salem, a Midwestern hamlet filled with evil geniuses, star-crossed lovers and a rich family history.",
                "1965-11-08",
                "https://image.tmdb.org/t/p/w500/iF8ai2QLNiHV4anwY1TuSGZXqfN.jpg",
                false
            )
        )
        return tvShow
    }

    fun generateRemoteDummyMovies(): List<ResultsMovieItem> {
        val movies = ArrayList<ResultsMovieItem>()

        movies.add(
            ResultsMovieItem(
                580489,
                "Venom: Let There Be Carnage",
                "After finding a host body in investigative reporter Eddie Brock, the alien symbiote must face a new enemy, Carnage, the alter ego of serial killer Cletus Kasady.",
                "2021-09-30",
                "/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg"
            )
        )

        return movies
    }

    fun generateRemoteDummyTvShow(): List<ResultsTvShowItem> {
        val tvSHow = ArrayList<ResultsTvShowItem>()

        tvSHow.add(
            ResultsTvShowItem(
                90462,
                "Chucky",
                "After a vintage Chucky doll turns up at a suburban yard sale, an idyllic American town is thrown into chaos as a series of horrifying murders begin to expose the town’s hypocrisies and secrets. Meanwhile, the arrival of enemies — and allies — from Chucky’s past threatens to expose the truth behind the killings, as well as the demon doll’s untold origins.",
                "2021-10-12",
                "/xAKMj134XHQVNHLC6rWsccLMenG.jpg"
            )
        )

        return tvSHow
    }
}