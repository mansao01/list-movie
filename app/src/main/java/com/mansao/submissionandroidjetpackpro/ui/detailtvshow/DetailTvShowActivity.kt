package com.mansao.submissionandroidjetpackpro.ui.detailtvshow

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
import com.mansao.submissionandroidjetpackpro.data.source.local.entity.TvShowEntity
import com.mansao.submissionandroidjetpackpro.databinding.ActivityDetailTvShowBinding
import com.mansao.submissionandroidjetpackpro.viewmodel.ViewModelFactory
import com.mansao.submissionandroidjetpackpro.vo.Status

class DetailTvShowActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailTvShowViewModel
    private var menu: Menu? = null

    private lateinit var binding: ActivityDetailTvShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvShowId = intent.getParcelableExtra<TvShowEntity>(EXTRA_DATA) as TvShowEntity
        supportActionBar?.title = tvShowId.showTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTvShowViewModel::class.java]
        tvShowId.showId?.let { viewModel.selectedTvShow(it) }

        viewModel.getTvShow().observe(this) { detailTvShow ->
            if (detailTvShow != null) {
                when (detailTvShow.status) {
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        detailTvShow.data?.let { populateTvShow(it) }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun populateTvShow(tvShowEntity: TvShowEntity) {
        binding.apply {
            tvTitle.text = tvShowEntity.showTitle
            tvYear.text = StringBuilder("Released : ").append("${tvShowEntity.showReleased}")
            tvDescription.text = tvShowEntity.showDescription
            Glide.with(this@DetailTvShowActivity)
                .load("https://image.tmdb.org/t/p/w500/${tvShowEntity.showPoster}")
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
                val data = intent.getParcelableExtra<TvShowEntity>(EXTRA_DATA) as TvShowEntity
                val newState = !data.favorite

                viewModel.setTvShowFavorite(data, newState)
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
        const val EXTRA_DATA = "extra_Data"
    }
}