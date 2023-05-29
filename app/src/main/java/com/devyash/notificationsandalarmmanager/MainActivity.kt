package com.devyash.notificationsandalarmmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devyash.notificationsandalarmmanager.databinding.ActivityMainBinding
import com.devyash.notificationsandalarmmanager.others.Constants.CHANNELID

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.btnSetTimer.setOnClickListener {
                showTimePicker()
        }

        binding.btnSetAlarm.setOnClickListener {

        }

        binding.btnCancelAlarm.setOnClickListener {

        }

    }

    private fun showTimePicker() {



    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TESTINGCHANNELNAME"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNELID, name, importance)
            channel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}