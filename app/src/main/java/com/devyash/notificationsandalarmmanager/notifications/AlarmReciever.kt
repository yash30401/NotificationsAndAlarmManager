package com.devyash.notificationsandalarmmanager.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.devyash.notificationsandalarmmanager.DestActivity
import com.devyash.notificationsandalarmmanager.R
import com.devyash.notificationsandalarmmanager.others.Constants.CHANNELID

class AlarmReciever : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val intent= Intent(context!!,DestActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent = PendingIntent.getActivity(context!!,0,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = context?.let {
            NotificationCompat.Builder(it,CHANNELID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Testing Title")
                .setContentText("Testing Description of Notification App")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(123,builder)


    }

}