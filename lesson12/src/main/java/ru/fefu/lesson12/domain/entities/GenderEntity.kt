package ru.fefu.lesson12.domain.entities

import com.google.gson.annotations.SerializedName

data class GenderEntity(
    @SerializedName("code")
    val code: Int,
    @SerializedName("name")
    val name: String
)
