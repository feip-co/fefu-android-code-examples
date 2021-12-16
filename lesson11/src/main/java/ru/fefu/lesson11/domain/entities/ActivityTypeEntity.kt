package ru.fefu.lesson11.domain.entities

import com.google.gson.annotations.SerializedName

data class ActivityTypeEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)