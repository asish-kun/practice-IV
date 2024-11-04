package com.example.practice_iv.models

data class Workout(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var favorite: Boolean = false // New favorite flag
)