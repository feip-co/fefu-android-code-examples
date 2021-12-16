package ru.fefu.lesson8

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.fefu.lesson8.databinding.ActivityAppBinding

class PermissionActivity : AppCompatActivity(R.layout.activity_app) {

    private lateinit var binding: ActivityAppBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            //Разрешение выдали
            if (granted) {
                call()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)
                ) {
                    //Разрашение уже запрашивали, не выдали, и уже объясняли юзеру зачем нужно это разрешение
                    showPermissionDeniedDialog()
                } else {
                    showRationaleDialog()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCall.setOnClickListener { requestCallPermissionAndCall() }
        binding.btnShowNotification.setOnClickListener { showNotification() }
    }


    /**
     * Permissions start
     */
    private fun call() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.fromParts("tel", "88005553535", null)
        startActivity(intent)
    }

    private fun requestCallPermissionAndCall() {
        when {
            //В случае если разрешение уже выдано
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED -> {
                call()
            }
            //В случае если разрешение уже просили, но его не выдали, и нужно объяснить юзеру зачем нужно разрешение
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                Manifest.permission.CALL_PHONE
            ) -> {
                showRationaleDialog()
            }
            //Иначе попробовать запросить разрешение
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission required")
            .setMessage("You cannot call from app without call permission")
            .setPositiveButton("Proceed") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission required")
            .setMessage("Please allow permission from app settings")
            .setPositiveButton("Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }
    /**
     * Permissions end
     */


    /**
     * Notifications start
     */
    private fun showNotification() {
        createNotificationChannelIfNeeded()
        val intent = Intent(this, PermissionActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
        val builder = NotificationCompat.Builder(this, "default_channel_id")
            .setContentTitle("Title")
            .setContentText("Notification message")
            .setSmallIcon(R.drawable.ic_baseline_add_24)
            //ignored for android >= OREO
            .setPriority(NotificationCompat.PRIORITY_LOW)
            //remove notification when user taps on it
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
    }

    private fun createNotificationChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default_channel_id",
                "Default channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    /**
     * Notifications end
     */

}