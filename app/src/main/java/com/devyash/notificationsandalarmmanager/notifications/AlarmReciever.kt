package com.devyash.notificationsandalarmmanager.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.devyash.notificationsandalarmmanager.R
import com.devyash.notificationsandalarmmanager.others.Constants.CHANNELID

class AlarmReciever : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val builder =NotificationCompat.Builder(context!!,CHANNELID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Testing Title")
            .setContentText("Testing Description of Notification App")
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManagerCompat = if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context!!, "Permissino Not Given", Toast.LENGTH_SHORT).show()
            return
        }else{
            NotificationManagerCompat.from(context!!)
                .notify(123,builder.build())
        }


    }

}