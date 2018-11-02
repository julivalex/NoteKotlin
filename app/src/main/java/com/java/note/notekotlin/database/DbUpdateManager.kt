package com.java.note.notekotlin.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.Database

class DbUpdateManager(private val db: SQLiteDatabase) {

    fun updateTitle(timestamp: Long, title: String) {
        update(Database.Column.TASK_TITLE, timestamp, title)
    }

    fun updateDate(timestamp: Long, date: Long) {
        update(Database.Column.TASK_DATE, timestamp, date)
    }

    fun updatePriority(timestamp: Long, priority: Int) {
        update(Database.Column.TASK_PRIORITY, timestamp, priority.toLong())
    }

    fun updateStatus(timestamp: Long, status: Int) {
        update(Database.Column.TASK_STATUS, timestamp, status.toLong())
    }

    fun updateTask(task: ModelTask) {
        updateTitle(task.timestamp, task.title)
        updateDate(task.timestamp, task.date)
        updatePriority(task.timestamp, task.priority)
        updateStatus(task.timestamp, task.status)
    }

    private fun update(column: String, key: Long, value: String) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(Database.TASKS_TABLE, cv, Database.Column.TASK_TIME_STAMP + " = " + key, null)
    }

    private fun update(column: String, key: Long, value: Long) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(Database.TASKS_TABLE, cv, Database.Column.TASK_TIME_STAMP + " = " + key, null)
    }

}