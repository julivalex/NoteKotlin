package com.java.note.notekotlin.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.java.note.notekotlin.R

fun getColorResource(context: Context, colorId: Int): Int =
    ContextCompat.getColor(context, colorId)

fun getToolbarTitleColor(context: Context): Int =
    ContextCompat.getColor(context, R.color.white)

fun toast(context: Context, name: String?) {
    Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
}

