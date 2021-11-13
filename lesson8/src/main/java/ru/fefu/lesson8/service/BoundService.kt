package ru.fefu.lesson8.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.util.Log

class BoundService : Service() {

    companion object {
        private const val TAG = "BinderService"
    }

    private val binder = MyServiceBinder()

    override fun onBind(intent: Intent?): MyServiceBinder = binder

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    fun onPlayClicked() {
        Log.d(TAG, "onPlayClicked")
    }

    fun onNextClicked() {

    }

    fun onPrevClicked() {

    }

    inner class MyServiceBinder : Binder() {

        fun getService() = this@BoundService

    }

}