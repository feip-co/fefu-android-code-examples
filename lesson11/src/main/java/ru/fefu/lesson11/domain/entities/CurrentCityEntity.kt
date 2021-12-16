package ru.fefu.lesson11.domain.entities

import com.google.gson.annotations.SerializedName

data class CurrentCityEntity(
    @SerializedName("city_id")
    val id: String,
    @SerializedName("city_name")
    val name: String
)
