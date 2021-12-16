package ru.fefu.lesson10.retrofit.remote.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response
import ru.fefu.lesson10.retrofit.remote.error.NetworkUnavailable

class NetworkStateInterceptor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) throw NetworkUnavailable
        return chain.proceed(chain.request())
    }

    private fun isConnected() : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkMAndAbove(cm)
        } else {
            checkBelowM(cm)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkMAndAbove(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    private fun checkBelowM(connectivityManager: ConnectivityManager): Boolean =
        connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false


}