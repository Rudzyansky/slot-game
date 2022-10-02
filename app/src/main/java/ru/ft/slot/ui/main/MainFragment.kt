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
        }

        binding.col1.setState(items, 0)

        binding.col2.setState(items, 1)
        binding.col2.spin(8)

        binding.col3.setState(items, 2)

        binding.col4.setState(items, 3)
        binding.col4.spin(-30)

        binding.col5.setState(items, 4)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
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