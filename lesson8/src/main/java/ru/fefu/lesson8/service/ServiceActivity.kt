package ru.fefu.lesson8.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.lesson8.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding

    private var boundService: BoundService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            boundService = (service as BoundService.MyServiceBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            boundService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnForeground.setOnClickListener {
            ForegroundService.startForeground(this, 111)
        }

        binding.btnBinder.setOnClickListener {
            boundService?.onPlayClicked()
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, BoundService::class.java),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        if (boundService != null) {
            unbindService(serviceConnection)
            boundService = null
        }
        super.onStop()
    }

}