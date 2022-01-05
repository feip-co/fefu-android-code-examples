package co.feip.lesson13.model.api

import co.feip.lesson13.model.dto.RegisterDto
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {

    @POST("/api/auth/register")
    suspend fun register(
        @Query("login") login: String,
        @Query("password") password: String,
        @Query("name") name: String,
        @Query("gender") gender: Int
    ): RegisterDto

    @POST("/api/auth/login")
    suspend fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): RegisterDto

}