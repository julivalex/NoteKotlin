package com.java.note.notekotlin.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.java.note.notekotlin.database.DbHelper
import com.java.note.notekotlin.model.ModelTask
import com.java.note.notekotlin.utils.Database
import com.java.note.notekotlin.utils.Selection
import com.java.note.notekotlin.utils.Status

class AlarmSetter : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val dbHelper = DbHelper(context)
        val alarmHelper = AlarmHelper(context)

        val tasks: MutableList<ModelTask> = ArrayList()
        tasks.addAll(
            dbHelper.queryManager
                .getTasks(
                    "${Selection.STATUS} OR ${Selection.STATUS}",
                    arrayOf(Status.STATUS_CURRENT.toString(), Status.STATUS_OVERDUE.toString()),
                    Database.Column.TASK_DATE
                )
        )

        for (task: ModelTask in tasks) {
            if (task.date != 0L) {
                alarmHelper.setAlarm(task)
            }
        }
    }
}