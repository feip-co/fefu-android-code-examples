package ru.fefu.lesson10

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fefu.lesson10.retrofit.remote.MemesApi
import ru.fefu.lesson10.retrofit.remote.interceptors.CustomHeaderInterceptor
import ru.fefu.lesson10.retrofit.remote.interceptors.NetworkStateInterceptor
import java.time.Duration
import java.util.concurrent.TimeUnit

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .callTimeout(10L, TimeUnit.SECONDS)
            .addInterceptor(NetworkStateInterceptor(this))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fefu.t.feip.co/")
            .client(okHttpClient)
            .build()
    }

    val memesApi: MemesApi by lazy {
        retrofit.create(MemesApi::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }


    enum class Type(val id: Int) {
        RUN(8)
    }

}