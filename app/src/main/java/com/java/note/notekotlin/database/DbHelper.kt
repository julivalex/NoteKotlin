package com.java.note.notekotlin.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.Database
import com.java.note.notekotlin.utils.Selection

class DbHelper(val context: Context) :
    SQLiteOpenHelper(context, Database.NAME, null, Database.VERSION) {

    val queryManager: DbQueryManager
        get() = DbQueryManager(readableDatabase)

    val updateManager: DbUpdateManager
        get() = DbUpdateManager(writableDatabase)


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Database.createScript())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(Database.deleteScript())
        onCreate(db)
    }

    fun saveTask(task: ModelTask) {
        val newValues = ContentValues()
        newValues.put(Database.Column.TASK_TITLE, task.title)
        newValues.put(Database.Column.TASK_DATE, task.date)
        newValues.put(Database.Column.TASK_PRIORITY, task.priority)
        newValues.put(Database.Column.TASK_STATUS, task.status)
        newValues.put(Database.Column.TASK_TIME_STAMP, task.timestamp)

        writableDatabase.insert(Database.TASKS_TABLE, null, newValues)
    }

    fun removeTask(timestamp: Long) {
        writableDatabase.delete(Database.TASKS_TABLE, Selection.TIME_STAMP, arrayOf(timestamp.toString()))
    }
}