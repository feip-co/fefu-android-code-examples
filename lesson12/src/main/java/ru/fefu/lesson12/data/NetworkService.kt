package ru.fefu.lesson12.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.floor

class NetworkService {

    private val logger = HttpLoggingInterceptor()

    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(ResponseInterceptor())
            .addInterceptor(logger.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    private val _retrofit = Retrofit.Builder()
        .baseUrl("https://fefu.t.feip.co")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofit
        get() = _retrofit

}

fun <T> Call<T>.asFlow(): Flow<T> = callbackFlow {
    enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                offer(response.body()!!)
                close()
            } else {
                close(Throwable())
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            close(t)
        }

    })

    awaitClose { if (!isCanceled) cancel() }

}

suspend fun <T> Call<T>.await(): Result<T> = suspendCoroutine { cont ->
    enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful)
                cont.resume(Result.Success(response.body()!!))
            else
                cont.resume(Result.Error(ApiError))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            cont.resumeWithException(t)
        }

    })
}

sealed class Result<T> {
    class Success<T>(val result: T) : Result<T>()
    class Error<T>(val e: Throwable) : Result<T>()
}

object ApiError : Throwable()

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val response = chain.proceed(chain.request())
        val modified = response.newBuilder()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()

        return modified
    }

}