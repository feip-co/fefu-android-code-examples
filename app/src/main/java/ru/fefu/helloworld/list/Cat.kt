package ru.fefu.helloworld.list

import androidx.annotation.DrawableRes

data class Cat(
    val id: Int,
    val name: String,
    val description: String,
    @DrawableRes val image: Int
)

enum class Color {
    BLACK, WHITE
}

sealed class SealedClass {

    class Black(val alpha: Int): SealedClass()

    class White(val alpha: Int): SealedClass()

}
