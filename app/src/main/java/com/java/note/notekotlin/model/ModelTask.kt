package com.java.note.notekotlin.model

import android.os.Parcelable
import com.java.note.notekotlin.R
import com.java.note.notekotlin.utils.Priority
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelTask(var title: String, var date: Long, var priority: Int, var status: Int) : Item, Parcelable {

    constructor() : this("", 0L, 0, -1)

    override fun isTask() = true

    fun getPriorityColor() =
        when (priority) {
            Priority.PRIORITY_HIGH -> {
                if (isStatusCurrentOrOverdue()) {
                    R.color.priority_high
                } else {
                    R.color.priority_high_selected
                }
            }

            Priority.PRIORITY_NORMAL -> {
                if (isStatusCurrentOrOverdue()) {
                    R.color.priority_normal
                } else {
                    R.color.priority_normal_selected
                }
            }
            Priority.PRIORITY_LOW -> {
                if (isStatusCurrentOrOverdue()) {
                    R.color.priority_low
                } else {
                    R.color.priority_low_selected
                }
            }
            else -> 0
        }

    private fun isStatusCurrentOrOverdue() = status == Priority.STATUS_CURRENT ||
            status == Priority.STATUS_OVERDUE
}