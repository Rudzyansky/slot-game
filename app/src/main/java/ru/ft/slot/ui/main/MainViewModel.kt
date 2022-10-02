package ru.ft.slot.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val betLiveData: MutableLiveData<Int> = MutableLiveData(7)
    val balanceLiveData: MutableLiveData<Int> = MutableLiveData(1000)

    var bet
        get() = betLiveData.value!!
        set(value) {
            if (value > 0) betLiveData.value = value
        }

    var balance
        get() = balanceLiveData.value!!
        set(value) {
            balanceLiveData.value = value
        }

}