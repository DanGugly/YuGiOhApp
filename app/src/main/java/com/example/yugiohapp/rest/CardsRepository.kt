package com.example.yugiohapp.rest

import com.example.yugiohapp.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface CardsRepository {
    val cardList: StateFlow<UIState>
    suspend fun getCardList()
}

class CardsRepositoryImpl(
    private val cardAPI: CardAPI
) : CardsRepository{

    private val _cardListFlow: MutableStateFlow<UIState> = MutableStateFlow(UIState.LOADING())

    override val cardList: StateFlow<UIState>
        get() = _cardListFlow

    override suspend fun getCardList() {
        try {
            val response = cardAPI.getCardList()
            if (response.isSuccessful){
                response.body()?.let {
                    _cardListFlow.value = UIState.SUCCESS(it)
                } ?: run {_cardListFlow.value = UIState.ERROR(IllegalStateException("Cards null")) }
            } else{
                _cardListFlow.value = UIState.ERROR(Exception(response.errorBody()?.string()))
            }
        }catch (e:Exception){
            _cardListFlow.value = UIState.ERROR(e)
        }
    }
}