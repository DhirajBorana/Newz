package com.example.newz

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.*
import com.example.newz.data.local.AppPreferences

class NewzApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)

        if (AppPreferences.nightMode) setDefaultNightMode(MODE_NIGHT_YES)
        else setDefaultNightMode(MODE_NIGHT_NO)
    }
}