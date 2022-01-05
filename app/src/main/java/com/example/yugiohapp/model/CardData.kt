package com.example.yugiohapp.model


import com.google.gson.annotations.SerializedName

data class CardData(
    @SerializedName("data")
    val `data`: List<Data>
)