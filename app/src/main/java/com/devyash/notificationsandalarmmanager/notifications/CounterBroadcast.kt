package com.devyash.notificationsandalarmmanager.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.devyash.notificationsandalarmmanager.others.Counter


class CounterBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
            val service = CounterNotificationService(context)
            service.showNotification(++Counter.count)
    }
}