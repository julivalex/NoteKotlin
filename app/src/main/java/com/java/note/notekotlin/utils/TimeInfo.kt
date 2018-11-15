package com.java.note.notekotlin.utils

import com.java.note.notekotlin.model.ModelTask
import java.text.SimpleDateFormat
import java.util.*

fun getDate(date: Long): String {
    val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    return dateFormat.format(date)
}

fun getTime(time: Long): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun getHour(time: Long): String {
    val timeFormat = SimpleDateFormat("HH", Locale.getDefault())
    return timeFormat.format(time)
}

fun getMinute(time: Long): String {
    val timeFormat = SimpleDateFormat("mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun getDateTime(time: Long): String {
    val timeFormat = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun combineCalendars(calendarDate: Calendar?, calendarTime: Calendar?, task: ModelTask? = null): Long =
    Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, get(Calendar.HOUR_OF_DAY) + 1)
        if (calendarDate != null) {
            set(Calendar.YEAR, calendarDate.get(Calendar.YEAR))
            set(Calendar.MONTH, calendarDate.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, calendarDate.get(Calendar.DAY_OF_MONTH))
        }
        if (calendarTime != null) {
            set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE))
        } else {
            if (task != null) {
                set(Calendar.HOUR_OF_DAY, getHour(task.date).toInt())
                set(Calendar.MINUTE, getMinute(task.date).toInt())
            }
        }
    }.timeInMillis


