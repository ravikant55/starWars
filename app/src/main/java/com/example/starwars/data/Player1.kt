package com.example.starwars.data


import com.google.gson.annotations.SerializedName

data class Player1(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("score")
    val score: Int?
)