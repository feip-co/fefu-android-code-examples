package ru.fefu.lesson12.domain.entities

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("gender")
    val gender: GenderEntity,
)
