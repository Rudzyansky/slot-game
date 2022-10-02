package ru.ft.slot.ui.main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ft.slot.R

class MainViewModel(private val context: Context) : ViewModel() {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    val betLiveData: MutableLiveData<Int> = MutableLiveData(10)
    val balanceLiveData: MutableLiveData<Int> = MutableLiveData(prefs.getInt("Balance", 1000))

    init {
        balanceLiveData.observeForever { prefs.edit().putInt("Balance", it).apply() }
    }

    var bet
        get() = betLiveData.value!!
        set(value) {
            if (value < 1 || value > balance) return
            betLiveData.value = value
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
        if (balance == 0) balance = 1000
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