package com.example.starwars.data


import com.google.gson.annotations.SerializedName

data class PlayersDataItem(
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)