package com.java.note.notekotlin.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDate(date: Long): String {
    val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    return dateFormat.format(date)
}

fun getTime(time: Long): String {
    val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun getDateTime(date: String?, time: String?) =
    "${date ?: ""} ${time ?: ""}"
