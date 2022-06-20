package com.mansao.submissionandroidjetpackpro.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mansao.submissionandroidjetpackpro.R
import com.mansao.submissionandroidjetpackpro.databinding.ActivityHomeBinding
import com.mansao.submissionandroidjetpackpro.ui.favorite.movie.FavoriteMovieActivity
import com.mansao.submissionandroidjetpackpro.ui.favorite.tvshow.FavoriteTvShowActivity
import com.mansao.submissionandroidjetpackpro.ui.login.LoginActivity
import com.mansao.submissionandroidjetpackpro.ui.profile.ProfileActivity
import com.mansao.submissionandroidjetpackpro.ui.setting.SettingsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.watch)

        val sectionPager = SectionPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionPager
        binding.tabs.setupWithViewPager(binding.viewPager)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val moveToSettingsActivity = Intent(this, SettingsActivity::class.java)
                startActivity(moveToSettingsActivity)
            }
            R.id.fav_movie -> {
                val moveToFAvoriteMovieActivity = Intent(this, FavoriteMovieActivity::class.java)
                startActivity(moveToFAvoriteMovieActivity)
            }
            R.id.fav_tvShow -> {
                val moveToFavoriteTvShowActivity = Intent(this, FavoriteTvShowActivity::class.java)
                startActivity(moveToFavoriteTvShowActivity)
            }
            R.id.profile -> {
                val moveToProfileIntent = Intent(this, ProfileActivity::class.java)
                startActivity(moveToProfileIntent)
            }

            R.id.logOut -> {
                auth.signOut()
                Intent(this, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }
        return true
    }

}