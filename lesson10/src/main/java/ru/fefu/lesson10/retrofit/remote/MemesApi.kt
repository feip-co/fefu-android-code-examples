package ru.fefu.lesson10.retrofit.remote

import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import ru.fefu.lesson10.retrofit.remote.models.request.CreateMemeModel
import ru.fefu.lesson10.retrofit.remote.models.response.CreateMemeResultModel
import ru.fefu.lesson10.retrofit.remote.models.response.MemesListModel
import ru.fefu.lesson10.retrofit.remote.models.response.ResponseModel

interface MemesApi {

    @GET("get_memes")
    fun getMemes(): Call<ResponseModel<MemesListModel>>

    @GET("api/activities")
    fun getActivities(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<JsonObject>

    @POST("caption_image")
    fun postMeme(
        @Body body: CreateMemeModel
    ): Call<ResponseModel<CreateMemeResultModel>>

    @POST("api/user/activities")
    fun postMultipart(
        @Body body: RequestBody
    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("api/user/activities")
    fun postUrlEncoded(
        @Field("starts_at") startsAt: String,
        @Field("ends_at") endsAt: String,
        @Field("activity_type_id") activityTypeId: Int,
        @Field("geo_track") geoTrack: String
    ): Call<JsonObject>

}