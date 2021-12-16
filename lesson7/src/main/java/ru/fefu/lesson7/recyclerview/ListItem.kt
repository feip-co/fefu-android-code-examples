package ru.fefu.lesson7.recyclerview

sealed class ListItem

class Activity(
    val id: Int,
    val date: Date
) : ListItem()

class Date(
    val date: String
) : ListItem()
