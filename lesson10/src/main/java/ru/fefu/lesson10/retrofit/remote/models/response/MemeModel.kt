package ru.fefu.lesson10.retrofit.remote.models.response

import com.google.gson.annotations.SerializedName

data class MemeModel(
    val id: Long,
    val name: String,
    val url: String,
    val width: Int,
    val height: Int,
    @SerializedName("box_count")
    val boxCount: Int
)