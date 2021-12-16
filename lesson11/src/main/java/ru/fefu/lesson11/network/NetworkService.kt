package ru.fefu.lesson11.network

import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.fefu.lesson11.domain.entities.ActivityTypeEntity
import ru.fefu.lesson11.domain.entities.CarEntity
import ru.fefu.lesson11.domain.entities.CurrentCityEntity
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.floor

class NetworkService {

    private val logger = HttpLoggingInterceptor()

    private val httpClient =
        OkHttpClient.Builder().addInterceptor(logger.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fefu.t.feip.co")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val testServiceApi = retrofit.create(TestApi::class.java)

//    suspend fun getCars() = withContext(Dispatchers.IO) {
//        testServiceApi.getCars()
//    }

    suspend fun getActivityTypes(query: String): Result<List<ActivityTypeEntity>> =
        testServiceApi.getActivityTypes(query).await()

    suspend fun getRandomColor() = withContext(Dispatchers.IO) {
        delay(1_000)
        val let = "1234567890ABCDEF"
        "#${List(6) { let[floor(Math.random() * 16).toInt()] }.joinToString("")}"
    }


}

interface TestApi {

    @GET("/api/activity_types")
    fun getActivityTypes(@Query("query") query: String): Call<List<ActivityTypeEntity>>

}

fun <T> Result<T>.request(
    coroutineScope: CoroutineScope,
    success: suspend (T) -> Unit,
    error: suspend (Throwable) -> Unit
) {
    coroutineScope.launch {
        when(this@request){
            is Result.Success -> {
                success(this@request.result)
            }
            is Result.Error -> {
                error(this@request.e)
            }
        }
    }

}

sealed class Result<T> {
    class Success<T>(val result: T) : Result<T>()
    class Error<T>(val e: Throwable) : Result<T>()
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

object ApiError : Throwable()


interface Async<T> {

    fun execute(c: Callback<T>)

    interface Callback<T> {

        fun onComplete(value: T) {}

        fun onCanceled() {}

        fun onError(e: Throwable) {}

    }

}

suspend fun <T> Async<T>.await(): T =
    //suspendCoroutine - Эта функция приостанавливает выполнение корутины и добидается пока какой то асинхронный вызов вернет обратно результат
    suspendCoroutine { continuation: Continuation<T> -> //Continuation - это способ общения с корутиной которая приостановила свое выполнение

        execute(object : Async.Callback<T> {

            override fun onComplete(value: T) {
                continuation.resume(value)
            }

            override fun onError(e: Throwable) {
                continuation.resumeWithException(e)
            }

        })

    }

suspend fun <T> Async<T>.awaitCancellable(): T =
    //Эта функция приостанавливает выполнение корутины и добидается пока какой то асинхронный вызов вернет обратно результат
    suspendCancellableCoroutine { continuation: CancellableContinuation<T> -> //CancellableContinuation - это способ общения с корутиной которая приостановила свое выполнение + в отличии от Continuation имеет функцию cancel

        execute(object : Async.Callback<T> {

            override fun onComplete(value: T) {
                continuation.resume(value)
            }

            override fun onError(e: Throwable) {
                continuation.resumeWithException(e)
            }

            override fun onCanceled() {
                continuation.cancel()
            }

        })

    }