package com.java.note.notekotlin.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val SPLASH_IS_INVISIBLE = "splash_is_invisible"

    private const val NAME = "prefKotlin"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor:SharedPreferences.Editor = edit()
        operation(editor)
        editor.apply()

    }

    var firstRun: Boolean
        get() = preferences.getBoolean(SPLASH_IS_INVISIBLE, false)
        set(value) = preferences.edit {
            it.putBoolean(SPLASH_IS_INVISIBLE, value)
        }
}