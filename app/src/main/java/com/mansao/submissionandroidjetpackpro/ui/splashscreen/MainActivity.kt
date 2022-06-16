package com.mansao.submissionandroidjetpackpro.ui.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.mansao.submissionandroidjetpackpro.R
import com.mansao.submissionandroidjetpackpro.ui.home.HomeActivity
import com.mansao.submissionandroidjetpackpro.ui.login.LoginActivity
import com.mansao.submissionandroidjetpackpro.ui.register.RegisterActivity
import com.mansao.submissionandroidjetpackpro.ui.setting.SettingPreferences
import com.mansao.submissionandroidjetpackpro.ui.setting.SettingsViewModel
import com.mansao.submissionandroidjetpackpro.viewmodel.ViewModelFactorySettings

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val delayMilliSecond: Long = 3000
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMilliSecond)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, ViewModelFactorySettings(pref)).get(
            SettingsViewModel::class.java
        )

        settingsViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


}