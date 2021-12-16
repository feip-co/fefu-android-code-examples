package ru.fefu.lesson11.domain.entities

import com.google.gson.annotations.SerializedName

data class CarEntity(
    @SerializedName("car")
    val car: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("vin")
    val vin: String,
    @SerializedName("image")
    val image: String
)