package com.example.starwars.data

data class Match(
    val match: Int,
    val player1: PlayerScore,
    val player2: PlayerScore
)


data class PlayerScore(
    val id: Int,
    val score: Int
)