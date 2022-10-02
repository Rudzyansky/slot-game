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

    fun calculate(lineUp: List<Int>, lineMaster: List<Int>, lineDown: List<Int>) {
        val line1 = lineUp.groupingBy { it }.eachCount().maxBy { it.value }.value
        val line2 = lineMaster.groupingBy { it }.eachCount().maxBy { it.value }.value
        val line3 = lineDown.groupingBy { it }.eachCount().maxBy { it.value }.value

        val profit = profitCalc(line1) + profitCalc(line2) + profitCalc(line3)
        if (profit == 0) return

        balance += profit
    }

    private fun profitCalc(matches: Int) =
        when (matches) {
            2 -> 2 * bet
            3 -> 4 * bet
            4 -> 6 * bet
            5 -> 10 * bet
            else -> 0
        }

}