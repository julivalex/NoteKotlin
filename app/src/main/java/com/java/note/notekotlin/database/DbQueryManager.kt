package com.java.note.notekotlin.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.Database

class DbQueryManager(val db: SQLiteDatabase) {

    fun getTasks(
        selection: String, selectionArgs: Array<String>, orderBy: String
    ): List<ModelTask> {

        val tasks: MutableList<ModelTask> = ArrayList()
        val cursor: Cursor = db.query(
            Database.TASKS_TABLE, null,
            selection, selectionArgs, null, null, orderBy
        )

        while (cursor.moveToNext()) {
            val title: String = cursor.getString(cursor.getColumnIndex(Database.Column.TASK_TITLE))
            val date: Long = cursor.getLong(cursor.getColumnIndex(Database.Column.TASK_DATE))
            val priority: Int = cursor.getInt(cursor.getColumnIndex(Database.Column.TASK_PRIORITY))
            val status: Int = cursor.getInt(cursor.getColumnIndex(Database.Column.TASK_STATUS))
            val timestamp: Long = cursor.getLong(cursor.getColumnIndex(Database.Column.TASK_TIME_STAMP))

            val modelTask = ModelTask(title, date, priority, status, timestamp)
            tasks.add(modelTask)

        }
        cursor.close()

        return tasks
    }
}