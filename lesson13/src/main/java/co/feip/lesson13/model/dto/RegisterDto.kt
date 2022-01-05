package co.feip.lesson13.model.dto

import com.google.gson.annotations.SerializedName

data class RegisterDto(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserDto
)
