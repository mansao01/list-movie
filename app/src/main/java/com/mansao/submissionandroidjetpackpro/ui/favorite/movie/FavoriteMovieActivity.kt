package com.mansao.submissionandroidjetpackpro.ui.favorite.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mansao.submissionandroidjetpackpro.R
import com.mansao.submissionandroidjetpackpro.databinding.ActivityFavoriteMovieBinding
import com.mansao.submissionandroidjetpackpro.viewmodel.ViewModelFactory

class FavoriteMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteMovieBinding
    private lateinit var viewModel: FavoriteMovieViewModel
    private lateinit var favMovieAdapter: FavoriteMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.fav_movie)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]
        favMovieAdapter = FavoriteMovieAdapter()

        viewModel.getFavoriteMovie().observe(this) { favMovie ->
            favMovieAdapter.submitList(favMovie)
            if (favMovie.isNotEmpty()) {
                binding.tvNoFav.visibility = View.GONE
            } else {
                binding.tvNoFav.visibility = View.VISIBLE
            }
        }
        with(binding.rvFavMovie){
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = favMovieAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}