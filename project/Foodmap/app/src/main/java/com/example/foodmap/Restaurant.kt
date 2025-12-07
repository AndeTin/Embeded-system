package com.example.foodmap

data class Restaurant(
    val name: String,
    val address: String,
    val cuisine: String,
    var isFavorite: Boolean = false
)