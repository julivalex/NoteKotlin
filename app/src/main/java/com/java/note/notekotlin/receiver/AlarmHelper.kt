package com.java.note.notekotlin.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.java.note.notekotlin.model.ModelTask

class AlarmHelper(val context: Context) {

    private var alarmManager: AlarmManager? = null

    init {
        alarmManager = context.applicationContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    fun setAlarm(modelTask: ModelTask) {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("title", modelTask.title)
        intent.putExtra("timestamp", modelTask.timestamp)
        intent.putExtra("color", modelTask.getPriorityColor())

        val pendingIntent: PendingIntent = PendingIntent
            .getBroadcast(
                context.applicationContext,
                modelTask.timestamp.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        alarmManager?.set(AlarmManager.RTC_WAKEUP, modelTask.date, pendingIntent)
    }

    fun removeAlarm(taskTimestamp: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent: PendingIntent = PendingIntent
            .getBroadcast(context.applicationContext, taskTimestamp.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager?.cancel(pendingIntent)
    }

}