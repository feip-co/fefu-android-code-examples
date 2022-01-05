package co.feip.lesson13.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {

    private val logger = HttpLoggingInterceptor()

    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(logger.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://fefu.t.feip.co")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

sealed class Result<T> {
    class Success<T>(val result: T) : Result<T>()
    class Error<T>(val e: Throwable) : Result<T>()
}