package com.devyash.notificationsandalarmmanager.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.devyash.notificationsandalarmmanager.DestActivity
import com.devyash.notificationsandalarmmanager.R
import com.devyash.notificationsandalarmmanager.others.Constants.COUNTER_CHANNEL

class CounterNotificationService(private val context: Context) {

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showNotification(counter: Int) {

        val intent = Intent(context, DestActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val incrementPendingIntent =
            PendingIntent.getBroadcast(context, 0, Intent(context, CounterBroadcast::class.java),PendingIntent.FLAG_IMMUTABLE)
        val notificationBuilder = NotificationCompat.Builder(
            context, COUNTER_CHANNEL
        ).setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Counter Notification")
            .setContentText("The Count is $counter")
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.baseline_notifications_24,
                "Increase",
                incrementPendingIntent
                )
            .build()

        notificationManager.notify(111,notificationBuilder)

    }

}