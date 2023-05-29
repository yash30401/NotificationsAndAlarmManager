package com.devyash.notificationsandalarmmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devyash.notificationsandalarmmanager.databinding.ActivityDestBinding

class DestActivity : AppCompatActivity() {

    private var _binding:ActivityDestBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}