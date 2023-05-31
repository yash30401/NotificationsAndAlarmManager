package com.devyash.notificationsandalarmmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.getSystemService
import com.devyash.notificationsandalarmmanager.others.Constants.COUNTER_CHANNEL

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createCounterNotificationChannel()
    }

    private fun createCounterNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName: CharSequence = "CounterChannel"
            val channelDesc = "Counter Notification Testing"
            val notificationImportance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(
                COUNTER_CHANNEL,
                channelName,
                notificationImportance
            )
            channel.description = channelDesc

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}