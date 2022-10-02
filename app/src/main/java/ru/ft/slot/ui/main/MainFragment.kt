package ru.ft.slot.ui.main

import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ft.slot.R
import ru.ft.slot.databinding.FragmentMainBinding
import ru.ft.slot.ui.spinning_wheel.SpinningWheel
import java.text.DecimalFormat
import kotlin.random.Random

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val decimalFormat = DecimalFormat("###,###")

    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(requireContext().applicationContext) as T
            }
        }
    }

    private val items by lazy {
        ITEMS.mapIndexed { index, it ->
            MyItem(index, AppCompatResources.getDrawable(requireContext(), it)!!)
        }
    }

    private var spinEndCount = 0

//    private fun randomItems() = // todo implement
    private fun randomIndex() = Random.nextInt(items.size, 3 * items.size) % items.size
    private fun randomCount() = -Random.nextInt(16, 64)
    private fun randomDuration() = CONST_TIME + Random.nextLong(0, (0.3f * CONST_TIME).toLong())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.betLiveData.observe(viewLifecycleOwner) { binding.bet.text = it.toString() }
        viewModel.balanceLiveData.observe(viewLifecycleOwner) {
            binding.money.text = decimalFormat.format(it)
        }
        binding.plusBtn.setOnClickListener { viewModel.bet++ }
        binding.minusBtn.setOnClickListener { viewModel.bet-- }
        binding.spinBtn.setOnClickListener {
            viewModel.balance -= viewModel.bet

            spinEndCount = 0
            binding.col1.spin(randomCount(), randomDuration()) { spinEnd() }
            binding.col2.spin(randomCount(), randomDuration()) { spinEnd() }
            binding.col3.spin(randomCount(), randomDuration()) { spinEnd() }
            binding.col4.spin(randomCount(), randomDuration()) { spinEnd() }
            binding.col5.spin(randomCount(), randomDuration()) { spinEnd() }
        }

        binding.col1.setState(items, randomIndex())
        binding.col2.setState(items, randomIndex())
        binding.col3.setState(items, randomIndex())
        binding.col4.setState(items, randomIndex())
        binding.col5.setState(items, randomIndex())

        return binding.root
    }

    private fun spinEnd() {
        spinEndCount++
//        if (spinEndCount == 5) viewModel.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
        private const val CONST_TIME = 10_000
        private val ITEMS = listOf(
            R.drawable.item0,
            R.drawable.item1,
            R.drawable.item2,
            R.drawable.item3,
            R.drawable.item4,
            R.drawable.item5,
            R.drawable.item6,
            R.drawable.item7,
            R.drawable.item8,
            R.drawable.item9
        )
    }

    data class MyItem(val id: Int, override val drawable: Drawable) : SpinningWheel.Item
}