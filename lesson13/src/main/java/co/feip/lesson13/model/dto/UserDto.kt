package co.feip.lesson13.model.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("gender")
    val gender: GenderDto,
)
