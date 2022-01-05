package ru.fefu.lesson10.retrofit

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.AsyncTaskLoader
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fefu.lesson10.App
import ru.fefu.lesson10.R
import ru.fefu.lesson10.retrofit.remote.models.request.CreateMemeModel
import ru.fefu.lesson10.retrofit.remote.models.response.MemesListModel
import ru.fefu.lesson10.retrofit.remote.models.response.ResponseModel

class RetrofitActivity : AppCompatActivity(R.layout.activity_http_connection) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMemes()
    }

    private fun getMemes() {
        val call = App.INSTANCE.memesApi.getActivities(1, 10)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                   if (response.isSuccessful) {

                   }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun sendMultipart() {
        App.INSTANCE.memesApi.postMultipart(
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("starts_at", "2021-11-23T17:22:11+00:00")
                .addFormDataPart("ends_at", "2021-11-24T15:22:11+00:00")
                .addFormDataPart("activity_type_id", "1")
                .addFormDataPart(
                    "geo_track",
                    "[{\"lat\":43.113011,\"lon\":131.872254},{\"lat\":43.210164,\"lon\":132.074602}]"
                )
                .build()
        ).enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                findViewById<TextView>(R.id.tvResult).text = response.body()?.toString()
                response
                // ответ сервера – успешный или нет (response.isSuccessful)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                // исключения в процессе выполнения запроса – отсутствие соединения, таймауты и т.д.
            }
        })
    }

    private fun sendUrlEncoded() {
        App.INSTANCE.memesApi.postUrlEncoded(
            "2021-11-23T17:22:11+00:00",
            "2021-11-24T15:22:11+00:00",
            1,
            "[{\"lat\":43.113011,\"lon\":131.872254},{\"lat\":43.210164,\"lon\":132.074602}]",
        ).enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                response
                // ответ сервера – успешный или нет (response.isSuccessful)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                // исключения в процессе выполнения запроса – отсутствие соединения, таймауты и т.д.
            }
        })
    }
}