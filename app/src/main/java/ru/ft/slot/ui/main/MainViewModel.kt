package ru.ft.slot.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val context: Context) : ViewModel() {
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


    // План:

    // По нажатию на крутить генерим рандомные сдвиги от size до 3*size
    // и время CONST_TIME + 0..0.3 * CONST_TIME
    // для каждого столбца

    // в фрагменте крутим колесо


    // кокда все докрутили
    // передаем все идшки в viewModel





}