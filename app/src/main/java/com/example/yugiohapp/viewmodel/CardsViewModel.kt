package com.example.yugiohapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yugiohapp.rest.CardsRepository
import com.example.yugiohapp.utils.UIState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class CardsViewModel(
    private val cardsRepository: CardsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)
) : ViewModel(), CoroutineScope by coroutineScope {

    private val _cardsLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING())
    val cardsLiveData : LiveData<UIState> get() = _cardsLiveData

    fun subscribeToCardsInfo(){
        launch {
            cardsRepository.getCardList()
        }
    }

    private fun collectCardInfo(){
        launch {
            cardsRepository.cardList.collect { uIState ->
                when(uIState){
                    is UIState.LOADING -> { _cardsLiveData.postValue(uIState) }
                    is UIState.SUCCESS -> { _cardsLiveData.postValue(uIState) }
                    is UIState.ERROR -> { _cardsLiveData.postValue(uIState) }
                }
            }
        }
    }
}