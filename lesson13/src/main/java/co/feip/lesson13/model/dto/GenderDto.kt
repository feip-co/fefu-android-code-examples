package co.feip.lesson13.model.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) – объекты, использующиеся для передачи данных между подсистемами
 * В данном случае используются для передачи между сервером и клиентом (приложением)
 */

data class GenderDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("name")
    val name: String
)
