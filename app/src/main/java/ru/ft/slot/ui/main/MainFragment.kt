package ru.ft.slot.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.lifecycle.SavedStateViewModelFactory
import ru.ft.slot.R
import ru.ft.slot.databinding.FragmentMainBinding
import java.text.DecimalFormat

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val decimalFormat = DecimalFormat("###,###")

    private lateinit var animeIn: Animation
    private lateinit var animeOut: Animation

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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

        animeIn = AnimationUtils.loadAnimation(context, R.anim.anime_in)
        animeOut = AnimationUtils.loadAnimation(context, R.anim.anime_out)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}