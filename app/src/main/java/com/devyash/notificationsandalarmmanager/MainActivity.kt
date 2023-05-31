package com.devyash.notificationsandalarmmanager

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import com.devyash.notificationsandalarmmanager.databinding.ActivityMainBinding
import com.devyash.notificationsandalarmmanager.notifications.AlarmReciever
import com.devyash.notificationsandalarmmanager.notifications.CounterNotificationService
import com.devyash.notificationsandalarmmanager.others.Constants.CHANNELID
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.btnSetTimer.setOnClickListener {
            showTimePicker()
        }

        binding.btnSetAlarm.setOnClickListener {
            setAlarm()
        }

        binding.btnCancelAlarm.setOnClickListener {
            cancelAlarm()
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "TESTINGCHANNELNAME"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNELID, name, importance)
            channel.description = description

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }


    private fun showTimePicker() {

        val picker: MaterialTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(supportFragmentManager, "ALARM")

        //Setting Up the time to the TextView
        picker.addOnPositiveButtonClickListener {
            val selectedHour = if (picker.hour > 12) picker.hour - 12 else picker.hour
            val amPm = if (picker.hour >= 12) "PM" else "AM"
            val formattedTime = String.format("%02d : %02d %s", selectedHour, picker.minute, amPm)
            binding.tvTime.text = formattedTime
        }


        //Setting Up The Calendar
        calendar = Calendar.getInstance(Locale.getDefault())
        picker.addOnPositiveButtonClickListener {
            calendar.set(Calendar.HOUR_OF_DAY, picker.hour)
            calendar.set(Calendar.MINUTE, picker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            // Perform any additional actions with the selected time
            Log.d("CALENDAR", calendar.get(Calendar.HOUR_OF_DAY).toString())
        }

    }

    private fun setAlarm() {

        val intent = Intent(this, AlarmReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent);
        }

        Toast.makeText(this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show()

    }

    private fun cancelAlarm() {
        val intent = Intent(this, AlarmReciever::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (alarmManager == null) {
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }

        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm Cancel Successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
