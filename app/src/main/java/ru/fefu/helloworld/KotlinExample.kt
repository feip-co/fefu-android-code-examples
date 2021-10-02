package ru.fefu.helloworld

class KotlinExample {

    private var s: String? = null

    fun method() {
        val c = s?.get(0)?.toString()
    }

}