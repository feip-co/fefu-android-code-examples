package ru.fefu.lesson12.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.fefu.lesson12.domain.entities.RegisterEntity

interface LoginApi {

    @POST("/api/auth/register")
    suspend fun register(
        @Query("login") login: String,
        @Query("password") password: String,
        @Query("name") name: String,
        @Query("gender") gender: Int
    ): RegisterEntity

    @POST("/api/auth/login")
    suspend fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): RegisterEntity

}