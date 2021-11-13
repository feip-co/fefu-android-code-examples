package ru.fefu.lesson8.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.fefu.lesson8.R

class ForegroundService : Service() {

    companion object {
        private const val TAG = "ForegroundService"
        private const val CHANNEL_ID = "foreground_service_id"
        private const val EXTRA_ID = "id"

        private const val ACTION_CANCEL = "cancel"
        fun startForeground(context: Context, id: Int) {
            val intent = Intent(context, ForegroundService::class.java)
            intent.putExtra(EXTRA_ID, id)
            ContextCompat.startForegroundService(context, intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand; ${intent?.getIntExtra(EXTRA_ID, -1)}")
        if (intent?.action == ACTION_CANCEL) {
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY
        }
        showNotification()
        super.onStartCommand(intent, flags, startId)
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    private fun showNotification() {
        createChannelIfNeeded()
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, ServiceActivity::class.java),
            0
        )
        val cancelIntent = Intent(this, ForegroundService::class.java).apply {
            action = ACTION_CANCEL
        }
        val cancelPendingIntent = PendingIntent.getService(
            this,
            1,
            cancelIntent,
            0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Hello")
            .setContentText("Tracking your activity")
            .setSmallIcon(R.drawable.ic_baseline_add_24)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_baseline_stop_24, "Stop", cancelPendingIntent)
            .build()
        startForeground(1, notification)
    }

    private fun createChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Default channel", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}