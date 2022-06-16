package com.mansao.submissionandroidjetpackpro.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mansao.submissionandroidjetpackpro.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val email = intent.getStringExtra(EXTRA_PROFILE)
        binding.tvEmail.text = email
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_PROFILE = "extra_profile"
    }
}