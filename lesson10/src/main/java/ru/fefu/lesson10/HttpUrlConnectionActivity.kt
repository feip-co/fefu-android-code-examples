package ru.fefu.lesson10

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class HttpUrlConnectionActivity : AppCompatActivity(R.layout.activity_http_connection) {

    companion object {
        private const val TAG = "HttpRequest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread { makeNetworkCall() }.start()
    }

    private fun makeNetworkCall() {
        val url = URL("https://api.imgflip.com/get_memes")
        val connection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Header1", "Header1Value")
        connection.setRequestProperty("Header2", "Header2Value")
        connection.readTimeout = 10000 // 10 sec
        connection.connectTimeout = 10000 // 10 sec

        var input: InputStream? = null
        var reader: BufferedReader? = null
        try {
            input = connection.inputStream
            reader = BufferedReader(InputStreamReader(input))
            val buffer = StringBuilder()
            var line = reader.readLine()
            while (line != null) {
                buffer.append(line)
                Log.d(TAG, line)
                line = reader.readLine()
            }
            runOnUiThread {
                findViewById<TextView>(R.id.tvResult).text = buffer
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            input?.close()
            reader?.close()
            connection.disconnect()
        }
    }
}