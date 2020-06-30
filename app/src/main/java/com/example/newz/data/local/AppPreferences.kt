package com.example.newz.data.local

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "com.example.newz"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val NIGHT_MODE = Pair("NIGHT_MODE", false)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var nightMode: Boolean
        get() = preferences.getBoolean(NIGHT_MODE.first, NIGHT_MODE.second)
        set(value) = preferences.edit {
            it.putBoolean(NIGHT_MODE.first, value)
        }
}