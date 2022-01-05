package com.example.yugiohapp.utils

import com.example.yugiohapp.model.CardData

sealed class UIState {
    class LOADING(val isLoading: Boolean = true) : UIState()
    class SUCCESS(val data: CardData): UIState()
    class ERROR(val error: Exception): UIState()
}
