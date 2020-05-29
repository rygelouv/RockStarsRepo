package com.github.rygelouv.rockstarapp.presentation

data class RockStar(
    val firstName: String,
    val lastName: String,
    val pictureUrl: String,
    val status: String,
    val isFavorite: Boolean = false
)

fun RockStar.buildHash() = firstName + status