package ru.fefu.lesson10.retrofit.remote.models.response

data class ResponseModel<T>(
    val success: Boolean,
    val data: T
)
