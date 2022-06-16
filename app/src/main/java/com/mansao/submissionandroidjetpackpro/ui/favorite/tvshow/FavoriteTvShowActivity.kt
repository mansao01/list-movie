package com.mansao.submissionandroidjetpackpro.ui.favorite.tvshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mansao.submissionandroidjetpackpro.R
import com.mansao.submissionandroidjetpackpro.databinding.ActivityFavoriteTvShowBinding
import com.mansao.submissionandroidjetpackpro.viewmodel.ViewModelFactory

class FavoriteTvShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteTvShowBinding
    private lateinit var viewModel: FavoriteTvShowViewModel
    private lateinit var favTvShowAdapter: FavoriteTvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteTvShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.fav_show)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FavoriteTvShowViewModel::class.java]
        favTvShowAdapter = FavoriteTvShowAdapter()

        viewModel.getFavoriteTvShow().observe(this) { favTvShow ->
            favTvShowAdapter.submitList(favTvShow)
            if (favTvShow.isNotEmpty()) {
                binding.tvNoFav.visibility = View.GONE
            } else {
                binding.tvNoFav.visibility = View.VISIBLE
            }
        }

        with(binding.rvFavTvShow){
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = favTvShowAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}