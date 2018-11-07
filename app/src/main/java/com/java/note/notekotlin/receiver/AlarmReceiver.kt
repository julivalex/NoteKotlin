package com.java.note.notekotlin.receiver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.java.note.notekotlin.MainActivity
import com.java.note.notekotlin.MyApplication
import com.java.note.notekotlin.R
import com.java.note.notekotlin.utils.getColorResource

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val title: String = intent.getStringExtra("title")
        val timestamp: Long = intent.getLongExtra("timestamp", 0)
        val color: Int = intent.getIntExtra("color", 0)

        var resultIntent = Intent(context, MainActivity::class.java)

        if (MyApplication.isActivityVisible()) {
            resultIntent = intent
        }

        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent: PendingIntent = PendingIntent
            .getActivity(context, timestamp.toInt(), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification: Notification = NotificationCompat.Builder(context, "")
            .setContentTitle("Note")
            .setContentText(title)
            .setColor(getColorResource(context, color))
            .setSmallIcon(R.mipmap.ic_check_circle_white_48dp)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .build()

        notification.flags = Notification.FLAG_AUTO_CANCEL

        val notificationManager: Any = context.getSystemService(Context.NOTIFICATION_SERVICE)
        if (notificationManager is NotificationManager) {
            notificationManager.notify(timestamp.toInt(), notification)
        }
    }

}