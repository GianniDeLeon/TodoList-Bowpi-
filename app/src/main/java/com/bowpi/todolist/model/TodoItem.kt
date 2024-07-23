package com.bowpi.todolist.model

data class TodoItem(
    val idDrink: String = "",
    val name: String = "",
    val description: String = "",
    val strDrinkThumb: String = "",
    var check: Boolean = false
)