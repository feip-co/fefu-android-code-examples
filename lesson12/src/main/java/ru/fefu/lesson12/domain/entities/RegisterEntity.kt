package ru.fefu.lesson12.domain.entities

import com.google.gson.annotations.SerializedName

data class RegisterEntity(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserEntity
)
