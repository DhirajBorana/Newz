package com.example.newz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.newz.R
import com.example.newz.databinding.ActivityMainBinding
import com.example.newz.data.local.AppPreferences
import com.example.newz.ui.section.SectionDialogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_theme -> toggleTheme()
                else -> false
            }
        }

        binding.bottomAppBar.setNavigationOnClickListener {
            val sectionDialog = SectionDialogFragment()
            sectionDialog.show(supportFragmentManager, sectionDialog.tag)
        }
    }

    private fun toggleTheme(): Boolean {
        if (AppPreferences.nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            AppPreferences.nightMode = false
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            AppPreferences.nightMode = true
        }
        return true
    }
}