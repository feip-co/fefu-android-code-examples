package ru.fefu.lesson10.retrofit.remote.models.request

import com.google.gson.annotations.SerializedName

data class CreateMemeModel(
    @SerializedName("template_id")
    val templateId: Int,
    val username: String,
    val password: String,
    val text0: String,
    val text1: String
)
