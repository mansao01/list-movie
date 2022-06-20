package com.mansao.submissionandroidjetpackpro.ui.detailmovie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mansao.submissionandroidjetpackpro.R
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.MovieEntity
import com.mansao.submissionandroidjetpackpro.databinding.ActivityDetailMovieBinding
import com.mansao.submissionandroidjetpackpro.viewmodel.ViewModelFactory
import com.mansao.submissionandroidjetpackpro.vo.Status

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getParcelableExtra<MovieEntity>(EXTRA_DATA) as MovieEntity

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = movieId.movieTitle

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        movieId.movieId?.let { viewModel.selectedMovies(it) }

        viewModel.getMovie().observe(this) { detailMovie ->
            if (detailMovie != null) {
                when (detailMovie.status) {
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        detailMovie.data?.let { populateMovie(it) }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun populateMovie(movieEntity: MovieEntity) {
        binding.apply {
            tvTitle.text = movieEntity.movieTitle
            tvYear.text =
                StringBuilder("${resources.getString(R.string.released)} : ").append("${movieEntity.movieReleased}")
            tvDescription.text = movieEntity.movieDescription
            Glide.with(this@DetailMovieActivity)
                .load("https://image.tmdb.org/t/p/w500/${movieEntity.moviePoster}")
                .into(imagePoster)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                val data = intent.getParcelableExtra<MovieEntity>(EXTRA_DATA) as MovieEntity
                val newState = !data.favorite

                viewModel.setMovieFavorite(data, newState)
                setFavoriteState(newState)
                toast(newState)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) {
            val menuItem = menu?.findItem(R.id.action_favorite)
            if (state) {
                menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
            } else {
                menuItem?.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }

    private fun toast(state: Boolean) {
        if (state) {
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}