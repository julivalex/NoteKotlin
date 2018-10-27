package com.java.note.notekotlin.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import com.java.note.notekotlin.R

fun getColorResource(context: Context, colorId: Int) =
    ContextCompat.getColor(context, colorId)

fun getToolbarTitleColor(context: Context) =
        ContextCompat.getColor(context, R.color.white)
