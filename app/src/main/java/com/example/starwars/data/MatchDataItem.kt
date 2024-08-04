package com.example.starwars.data

import com.google.gson.annotations.SerializedName

data class MatchDataItem(
    @SerializedName("match")
    val match: Int?,
    @SerializedName("player1")
    val player1: Player1?,
    @SerializedName("player2")
    val player2: Player1?
)