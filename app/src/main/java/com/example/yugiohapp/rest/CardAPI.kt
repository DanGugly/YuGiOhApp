package com.example.yugiohapp.rest

import com.example.yugiohapp.model.CardData
import retrofit2.Response
import retrofit2.http.GET

interface CardAPI {
    @GET(CARD_LIST)
    suspend fun getCardList() : Response<CardData>

    companion object{
        const val BASE_URL = "https://db.ygoprodeck.com/api/v7/"
        const val CARD_LIST = "cardinfo.php"
    }
}