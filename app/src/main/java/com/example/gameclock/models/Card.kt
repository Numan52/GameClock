package com.example.gameclock.models

// for memory puzzle
data class Card(
    val id: Int,
    val image: Int,
    var isRevealed: Boolean = false,
    var isMatched: Boolean = false
)
