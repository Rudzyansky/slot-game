package ru.ft.slot.ui.main

import android.animation.Animator
import android.animation.ValueAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.addListener
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
//todo init viewas
        // add to col1


        // on view created: animation listeners
        col1()

        animeIn = AnimationUtils.loadAnimation(context, R.anim.anime_in)
        animeOut = AnimationUtils.loadAnimation(context, R.anim.anime_out)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun col1() {
        val imageViews = ITEMS.map {
            ImageView(context).apply {
                setImageResource(it)
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            }
        }
        val column1 = binding.col1
        imageViews.forEach { column1.addView(it) }

        column1.invalidate()
        var currentIndex = 0
        val height = imageViews[0].height
        val halfHeight = height / 2f
        ValueAnimator.ofFloat(-120f, 0f).apply {
            duration = 2000
            addUpdateListener {
                imageViews.forEachIndexed { index, imageView ->
                    //if (currentIndex + index)
                    //
                    imageView.translationY =
                        imageView.height * index.toFloat() + it.animatedValue as Float
                    Log.e("AAA", imageView.height.toString())
                }
            }
            addListener(onRepeat = {
                currentIndex = (currentIndex + 1) % imageViews.size
            })
            interpolator = LinearInterpolator()
            repeatMode = ValueAnimator.RESTART
            repeatCount = 8//ValueAnimator.INFINITE
            start()
        }
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
}